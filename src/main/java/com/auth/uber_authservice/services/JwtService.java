package com.auth.uber_authservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.expiry}")
    private int expiry;
    @Value("${jwt.secret}")
    private String SECRET;

    //This method create a new jwt token for us based on a  payload

    public String createToken(Map<String,Object> payload,String username) {
        Date now =new Date();
        Date expiryDate=new Date(now.getTime()+expiry*1000L);
        SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(username)
                .signWith(key)
                .compact();
    }
}
