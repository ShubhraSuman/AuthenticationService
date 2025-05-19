package com.example.authentication.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final String SECRET = "your-secret-key";

    public String generateToken(String emailId, long expirationMillis) {
        return Jwts.builder()
                .setSubject(emailId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
