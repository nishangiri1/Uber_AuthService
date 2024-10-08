package com.auth.uber_authservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.expiry}")
    private int expiry;
    @Value("${jwt.secret}")
    private String SECRET;


    public String createToken(Map<String,Object> payload, UserDetails username) {
        Date now =new Date();
        payload.put("role",username.getAuthorities().iterator().next().toString());
        Date expiryDate=new Date(now.getTime()+expiry*1000L);
        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(username.getUsername())
                .signWith(getSecretKey())
                .compact();
    }

    public String createToken(UserDetails userDetails)
    {
        return createToken(new HashMap<>(),userDetails);
    }

    private Claims extractAllPayload(String tokens)
    {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(tokens)
                .getPayload();
    }

    public <T> T extractPayload(String token, Function<Claims,T> claimsResolver)
    {
     final Claims claims=extractAllPayload(token);
     return claimsResolver.apply(claims);
    }

    private Date extractExpiryDate(String token)
    {
        return extractPayload(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token)
    {
        return extractExpiryDate(token).before(new Date());
    }

    public String extractEmail(String token)
    {
        return extractPayload(token,Claims::getSubject);
    }

    private String extractSinglePayload(String token,String payload)
    {
        Claims claims=extractAllPayload(token);
        return (String) claims.get(payload);
    }


    public boolean validateToken(String token,String email)
    {
       final String userEmailFetchFromToken= extractEmail(token);
       return (userEmailFetchFromToken.equals(email) && !isTokenExpired(token));
    }


    private SecretKey getSecretKey() {
       return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Map<String,Object> mp=new HashMap<>();
//        mp.put("email","giri@gmail.com");
//        mp.put("PhoneNumber","9809785272");
//        String result=createToken(mp,"Nishan");
//        System.out.println("Generated token is:"+result);
//    }
}
