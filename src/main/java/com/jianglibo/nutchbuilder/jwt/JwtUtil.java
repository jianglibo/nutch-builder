package com.jianglibo.nutchbuilder.jwt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.domain.BootUser.Gender;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorDataBuilder;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.resource.Document;

@Component
public class JwtUtil implements InitializingBean {

	@Autowired
	private ApplicationConfig applicationConfig;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private JWTVerifier verifier;
	
	private Algorithm algorithm;
	
	public JWTCreator.Builder setPrincipalClaims(BootUserPrincipal principal) {
		JWTCreator.Builder jwtb = JWT.create();
		jwtb.withClaim("username", principal.getUsername());
		jwtb.withClaim("id", String.valueOf(principal.getId()));
		jwtb.withClaim("email", principal.getEmail());
		jwtb.withClaim("mobile", principal.getMobile());
		jwtb.withClaim("avatar", principal.getAvatar());
		jwtb.withArrayClaim("authorities", principal.getAuthorities().stream().map(a -> a.getAuthority()).toArray(size -> new String[size]));
		return jwtb;
	}
	
	public BootUserPrincipal toPrincipal(DecodedJWT decodedJwt) {
		String gd = decodedJwt.getClaim("gender").asString();
		Gender gender = Gender.valueOf(gd);
		String ids = decodedJwt.getClaim("id").asString();
		long id = Long.valueOf(ids);
		Collection<? extends GrantedAuthority> authorities = decodedJwt.getClaim("authorities").asList(String.class).stream().map(Role::new).collect(Collectors.toSet());
		
		return new BootUserPrincipal(
				  decodedJwt.getClaim("username").asString()
				, decodedJwt.getClaim("displayName").asString()
				, decodedJwt.getClaim("email").asString()
				, decodedJwt.getClaim("mobile").asString()
				, null
				, true
				, true
				, true
				, true
				, decodedJwt.getClaim("avatar").asString()
				, authorities
				, true
				, true
				, gender
				, new HashSet<>()
				, id
				, "");
	}

	
	private Date getExpireDate() {
		return new Date(new Date().getTime() + applicationConfig.getJwtConfig().getLasting());
	}
	
	public String issueToken(BootUserPrincipal principal) {
		JWTCreator.Builder jwt = setPrincipalClaims(principal);
		return jwt.withIssuer(applicationConfig.getJwtConfig().getIssuer())
		        .withExpiresAt(getExpireDate())
		        .sign(algorithm);
	}
	
	
	public BootUserPrincipal verifyToken(String token) {
        DecodedJWT jwt = verifier.verify(token);
        return toPrincipal(jwt);
	}
	
	
	public void writeForbidenResponse(HttpServletResponse response, String message) throws JsonGenerationException, JsonMappingException, IOException {
		ErrorData ed = new ErrorDataBuilder().setCode(String.valueOf(HttpServletResponse.SC_FORBIDDEN)).setTitle("Forbidden").setDetail(message).build();
		ErrorResponse er = ErrorResponse.builder().setStatus(500).setSingleErrorData(ed).build();
		Document newd = er.toResponse()
						.getDocument();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		objectMapper.writeValue(baos, newd);
		
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		ServletOutputStream out = response.getOutputStream();
		out.write(baos.toByteArray());
		out.flush();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String s = new String(Files.readAllBytes(Paths.get(applicationConfig.getJwtConfig().getFile())));
		algorithm = Algorithm.HMAC256(s);
        verifier = JWT.require(algorithm)
	            .withIssuer(applicationConfig.getJwtConfig().getIssuer())
	            .build();

	}
	

}
