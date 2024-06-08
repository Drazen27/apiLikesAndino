package com.apilikes.ApiRestLikes.utils;

import io.github.cdimascio.dotenv.Dotenv;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.stereotype.Component;


import java.security.Key;

@Component
public class JwtUtil {

    
    private final Key secret;

    public JwtUtil() {
        Dotenv dotenv = Dotenv.load();
        this.secret = Keys.hmacShaKeyFor(System.getenv("SECRET").getBytes());
    }
    
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println(e);
            return false;
        }
    }
}