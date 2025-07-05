package com.spring.auth.Token;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class jwtToken {
    
    private static final Long EXP = 24 * 60 * 60 * 1000L; 

    private SecretKey keyGenerate(){
        String SECERT_KEY = "your-secert-key-generate-token";
        return Keys.hmacShaKeyFor(SECERT_KEY.getBytes());
    }

    public String TokenGenrate(String email){
        return Jwts.builder()
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + EXP))
                    .subject(email)
                    .signWith(keyGenerate())
                    .compact();
    }
}
