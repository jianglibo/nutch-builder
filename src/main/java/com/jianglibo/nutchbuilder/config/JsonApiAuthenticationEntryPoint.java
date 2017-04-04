package com.jianglibo.nutchbuilder.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.jianglibo.nutchbuilder.jwt.JwtUtil;

@Component
public class JsonApiAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		jwtUtil.writeForbidenResponse(response, "");
	}

}
