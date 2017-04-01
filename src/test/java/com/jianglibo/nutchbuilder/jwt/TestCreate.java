package com.jianglibo.nutchbuilder.jwt;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TestCreate {
	
	private Algorithm algorithm;
	
	@Before
	public void b() {
		try {
			algorithm = Algorithm.HMAC256("secret");
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected=InvalidClaimException.class)
	public void tClaimExpired() {
		try {
			Calendar cl = Calendar.getInstance();
			cl.add(Calendar.HOUR, -1);
		    String token = JWT.create()
		        .withIssuer("auth0")
		        .withExpiresAt(cl.getTime())
		        .sign(algorithm);
		    System.out.println(token);
		    
	        JWTVerifier verifier = JWT.require(algorithm)
	            .withIssuer("auth0")
	            .build(); //Reusable verifier instance
	        DecodedJWT jwt = verifier.verify(token);
	        assertThat(jwt.getIssuer(), equalTo("auth0"));
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
	}
	
	@Test(expected=InvalidClaimException.class)
	public void tClaimIssuredInFuture() {
		try {
			Calendar cl = Calendar.getInstance();
			cl.add(Calendar.HOUR, 1);
		    String token = JWT.create()
		        .withIssuer("auth0")
		        .withIssuedAt(cl.getTime())
		        .sign(algorithm);
		    System.out.println(token);
		    
	        JWTVerifier verifier = JWT.require(algorithm)
	            .withIssuer("auth0")
	            .build(); //Reusable verifier instance
	        DecodedJWT jwt = verifier.verify(token);
	        assertThat(jwt.getIssuer(), equalTo("auth0"));
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
	}
	
	@Test(expected=InvalidClaimException.class)
	public void tClaimNotBefore() {
		try {
			Calendar cl = Calendar.getInstance();
			cl.add(Calendar.HOUR, 1);
		    String token = JWT.create()
		        .withIssuer("auth0")
		        .withNotBefore(cl.getTime())
		        .sign(algorithm);
		    System.out.println(token);
		    
	        JWTVerifier verifier = JWT.require(algorithm)
	            .withIssuer("auth0")
	            .build(); //Reusable verifier instance
	        DecodedJWT jwt = verifier.verify(token);
	        assertThat(jwt.getIssuer(), equalTo("auth0"));
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
	}

	@Test
	public void tCreate() {
		try {
		    String token = JWT.create()
		        .withIssuer("auth0")
		        .withArrayClaim("sarray", new String[]{"aaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"})
		        .sign(algorithm);
		    System.out.println(token);
		    
	        JWTVerifier verifier = JWT.require(algorithm)
	            .withIssuer("auth0")
	            .build(); //Reusable verifier instance
	        DecodedJWT jwt = verifier.verify(token);
	        assertThat(jwt.getIssuer(), equalTo("auth0"));
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
	}
}
