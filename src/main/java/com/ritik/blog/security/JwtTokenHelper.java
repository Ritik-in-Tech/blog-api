package com.ritik.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenHelper {

	@Value("${jwt.token.validity}")
	private long jwtTokenValidity;

	@Value("${jwt.secret.key}")
	private String secretKey;

	// retrieve userName from JWT token
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt
	public Date getExirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	// Function getClaimFromToken
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}

	// retrieving any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	// check the token is expired or not
	private Boolean isTokenExpired(String token) {
		final Date exirationDateFromToken = getExirationDateFromToken(token);
		return exirationDateFromToken.before(new Date());
	}

	// generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		return doGenerateToken(claims, userDetails.getUsername());
	}

	// dO generate function which generate the token according to the userName
	// Generate the token with claims and subject
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000)) // Set expiration
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secretKey) // Sign with the secret key
				.compact();
	}
	

	// Validate Token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
