package com.tripmaven.auth;




import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTOKEN {
	private static SecretKey secretKey;
		
	public JWTTOKEN(@Value("${jwt.secret}")String secret) {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.secretKey = Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String createJWT(String loginId, String role, Long expiredTime) {
		long currentTimeMillis = System.currentTimeMillis(); //토큰 생성시간
		expiredTime = currentTimeMillis + expiredTime; //토큰 만료 시간
		
		//Header에 안 실었는데.. 내가 토큰을 발급하고 응답헤더에 실어서 주는게 아니니까 안 실어도 되지 않을까??
		
		//JWT생성
		JwtBuilder builder = Jwts.builder()
				.claim("loginId", loginId)
				.claim("role", role)
				.issuedAt(new Date())
				.expiration(new Date(expiredTime))
				.signWith(secretKey, Jwts.SIG.HS256);
		
		return builder.compact();

	}/////createToken


}
