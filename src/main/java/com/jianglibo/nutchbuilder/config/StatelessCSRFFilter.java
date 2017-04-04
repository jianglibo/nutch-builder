package com.jianglibo.nutchbuilder.config;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jianglibo.nutchbuilder.jwt.JwtUtil;

/**
 * almost copy from https://blog.jdriven.com/2014/10/stateless-spring-security-part-1-stateless-csrf-protection/
 * 
 * app.config(['$httpProvider', function($httpProvider) {
    //fancy random token, losely after https://gist.github.com/jed/982883
    function b(a){return a?(a^Math.random()*16>>a/4).toString(16):([1e16]+1e16).replace(/[01]/g,b)};

    $httpProvider.interceptors.push(function() {
        return {
            'request': function(response) {
                // put a new random secret into our CSRF-TOKEN Cookie before each request
                document.cookie = 'CSRF-TOKEN=' + b();
                return response;
            }
        };
    });    
	}]);
 * @author jianglibo@hotmail.com
 *
 */

public class StatelessCSRFFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	public static final String CSRF_TOKEN = "CSRF-TOKEN";
	public static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";
	private final RequestMatcher requireCsrfProtectionMatcher = new DefaultRequiresCsrfMatcher();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (requireCsrfProtectionMatcher.matches(request)) {
			final String csrfTokenValue = request.getHeader(X_CSRF_TOKEN);
			final Cookie[] cookies = request.getCookies();

			String csrfCookieValue = null;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(CSRF_TOKEN)) {
						csrfCookieValue = cookie.getValue();
					}
				}
			}
			if (csrfTokenValue == null || !csrfTokenValue.equals(csrfCookieValue)) {
				jwtUtil.writeForbidenResponse(response, "Missing or non-matching CSRF-token");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	public static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
		private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

		@Override
		public boolean matches(HttpServletRequest request) {
			return !allowedMethods.matcher(request.getMethod()).matches();
		}
	}
}
