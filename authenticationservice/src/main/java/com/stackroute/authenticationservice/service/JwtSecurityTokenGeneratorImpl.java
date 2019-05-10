package com.stackroute.authenticationservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

import com.stackroute.authenticationservice.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtSecurityTokenGeneratorImpl implements SecurityTokenGenerator {

	@Override
	public Map<String, String> generateToken(User user) {
		String jwtToken = null;
		
		jwtToken = Jwts.builder().setSubject(user.getUserName()).setIssuedAt
				(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact();
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("token", jwtToken);
		map.put("message", "User successfully registered!");
		return map;
	}

}
