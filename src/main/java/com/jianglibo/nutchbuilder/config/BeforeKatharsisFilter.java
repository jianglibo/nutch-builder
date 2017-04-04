package com.jianglibo.nutchbuilder.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jianglibo.nutchbuilder.domain.LoginAttempt;
import com.jianglibo.nutchbuilder.domain.ThirdPartLogin.Provider;
import com.jianglibo.nutchbuilder.json.exception.AppExceptionMapper;
import com.jianglibo.nutchbuilder.jwt.JwtUtil;
import com.jianglibo.nutchbuilder.repository.LoginAttemptRepository;
import com.jianglibo.nutchbuilder.util.KatharsisConvertUtil;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorDataBuilder;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.resource.Document;
import io.katharsis.resource.Resource;

/**
 * JWT sessionStorage and localStorage Security -> cross-site scripting (XSS) attacks
 * JWT Cookie Storage Security -> cross-site request forgery (CSRF), Cookies, when used with the HttpOnly cookie flag
 * 
 * @author jianglibo@hotmail.com
 *
 */

@Priority(19)
@Component
public class BeforeKatharsisFilter implements Filter, ApplicationContextAware {

	private AuthenticationManager getAuthenticationManager() {
		return applicationContext.getBean(AuthenticationManager.class);
	}
	
	private static Logger logger = LoggerFactory.getLogger(BeforeKatharsisFilter.class);

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private KatharsisConvertUtil katharsisConvertUtil;
	
	@Autowired
	private JwtUtil jwtUtil;

	private ApplicationContext applicationContext;

	private Pattern pathPattern;
	
	@Autowired
	private LoginAttemptRepository loginAttemptRepository;

	@Autowired
	private ChangeSessionIdAuthenticationStrategy sessionAuthenticationStrategy;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Autowired
	public void setPattern(@Value("${katharsis.pathPrefix}") String prefix) {
		String t = String.format("^%s/loginAttempts", prefix);
		pathPattern = Pattern.compile(String.format("%s/.*|%s", t, t));
	}

	private boolean isPathMatch(String path) {
		return pathPattern.matcher(path).matches();
	}

	public static class Reswrapper extends HttpServletResponseWrapper {

		private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

		public Reswrapper(HttpServletResponse response) {
			super(response);
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new ServletOutputStream() {

				@Override
				public void write(int b) throws IOException {
					bos.write(b);
				}

				@Override
				public void setWriteListener(WriteListener listener) {
				}

				@Override
				public boolean isReady() {
					return true;
				}
			};
		}

		// It's already a katharsis document.
		public ByteArrayOutputStream getBos() {
			return bos;
		}

		public void writeToOrigin() throws IOException {
			getResponse().getOutputStream().write(bos.toByteArray());
			getResponse().flushBuffer();
		}
	}

	private ErrorResponse getErrorResponse(ErrorData ed) {
		return ErrorResponse.builder().setStatus(500).setSingleErrorData(ed).build();
	}

	/**
	 * loginAttemptDto repository do nothing.
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("filter_order");
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			if (isPathMatch(request.getRequestURI())) {
				Reswrapper rr = new Reswrapper(response);
				chain.doFilter(request, rr);
				ByteArrayOutputStream baos = rr.getBos();
				String s = baos.toString();
				Document d = objectMapper.readValue(s, Document.class);
				invoke(d, request, rr);
				// Response kr = new Response(d, 200);
				// objectMapper.writeValueAsBytes(er.toResponse());
				// int len = s.length();
				// rr.writeToOrigin();
				//
				// boolean passToFilters = invoke(request, response);
				// if (passToFilters) {
				//
				// }
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	private void invoke(Document d, HttpServletRequest request, Reswrapper rr) throws JsonGenerationException, JsonMappingException, IOException {
		Resource r = d.getSingleData().get();
		String username = r.getAttributes().get("username").asText();
		String password = r.getAttributes().get("password").asText();
		String provider = r.getAttributes().get("provider").asText();
		
		LoginAttempt loginAttemp = new LoginAttempt();
		loginAttemp.setProvider(Provider.valueOf(provider));
		loginAttemp.setRemoteAddress(request.getRemoteAddr());
		loginAttemp.setUsername(username);
		
		try {
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
						password);

				WebAuthenticationDetails wd = new WebAuthenticationDetailsSource().buildDetails(request);
				authRequest.setDetails(wd);

				Authentication an = getAuthenticationManager().authenticate(authRequest);
				BootUserPrincipal user = (BootUserPrincipal) an.getPrincipal();
				loginAttemp.setSuccess(true);
				loginAttemp.setPassword("");
				loginAttemptRepository.save(loginAttemp);
				r.setId(String.valueOf(loginAttemp.getId()));
				r.setAttribute("success", BooleanNode.TRUE);
				r.setAttribute("password", new TextNode(""));
//				BootUserAuthentication buan = new BootUserAuthentication(user);
//				SecurityContextHolder.getContext().setAuthentication(buan);
//				sessionAuthenticationStrategy.onAuthentication(buan, request, rr);
				
				r.setAttribute("jwt_token", new TextNode(jwtUtil.issueToken(user)));
				writeOut(d, rr);

		} catch (AuthenticationException e) {
				HttpServletResponse wrapped = (HttpServletResponse) rr.getResponse();
				wrapped.setStatus(AppExceptionMapper.APP_ERROR_STATUS_CODE);
				Document newd = getErrorResponse(
						new ErrorDataBuilder().setCode("500").setTitle("badCredentials").build()).toResponse()
								.getDocument();
				writeOut(newd, rr);
				loginAttemptRepository.save(loginAttemp);
		}
	}

	// forget rr object, talk to wrapped response directly.
	private void writeOut(Document newd, Reswrapper rr) throws JsonGenerationException, JsonMappingException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		objectMapper.writeValue(baos, newd);
		ServletOutputStream out = rr.getResponse().getOutputStream();
		out.write(baos.toByteArray());
		out.flush();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
