package com.jianglibo.nutchbuilder.jwt;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.vo.BootUserAuthentication;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;

/**
 * copy some code from @see {@link BasicAuthenticationFilter}
 * 
 * @author jianglibo@gmail.com
 *         2015年8月20日
 *
 */
@Component
@Priority(18)
public class JwtBasicFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(JwtBasicFilter.class);
	
	private Pattern pathPattern;
	
	private Pattern negPathPattern;
	
	@Autowired
	private JwtUtil jwtUtil;

	
	@Autowired
	public void setPattern(@Value("${katharsis.pathPrefix}") String prefix) {
		String t = String.format("^%s.*", prefix);
		pathPattern = Pattern.compile(t);
		
		String t1 = String.format("^%s/loginAttempts", prefix);
		negPathPattern = Pattern.compile(String.format("%s/.*|%s", t1, t1));
	}

    
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("filter_order");
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			if (HttpMethod.POST.matches(request.getMethod()) && negPathPattern.matcher(request.getRequestURI()).matches()) {
				chain.doFilter(request, response);
			} else if (pathPattern.matcher(request.getRequestURI()).matches()) {
				try {
					processBasicLogin(request);
					chain.doFilter(req, res);
				} catch (AuthenticationException e) {
		            SecurityContextHolder.clearContext();
		            throw new AccessDeniedException("authentication exception.");
				}
			} else {
				chain.doFilter(request, response);
			}
		}
	}

    private void processBasicLogin(HttpServletRequest request) throws AccessDeniedException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new AccessDeniedException("no jwt header.");
        }
        SecurityContextHolder.clearContext();
        BootUserPrincipal pricipal = jwtUtil.verifyToken(header.substring(7));
        
        BootUserAuthentication buan = new BootUserAuthentication(pricipal);
        SecurityContextHolder.getContext().setAuthentication(buan);
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
