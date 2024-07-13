package com.ameda.kisevu.Gemini_apis.service.impl;/*
*
@author ameda
@project Gemini-apis
*
*/

import com.ameda.kisevu.Gemini_apis.entities.User;
import com.ameda.kisevu.Gemini_apis.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Service
public class JwtServiceImpl implements JwtService {

    public String generateToken(UserDetails userDetails){
         return Jwts.builder()
                         .setSubject(userDetails.getUsername())
                         .setIssuedAt(new Date(System.currentTimeMillis()))
                         .setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 24))
                         .signWith(getSignKey(), SignatureAlgorithm.HS256)
                         .compact();

    }

    private Key getSignKey(){
        byte [] key = Decoders.BASE64.decode("413F4428481U3U3U30303U393044844U449K4L32HTY3U3B35J");
        return Keys.hmacShaKeyFor(key);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token){
        return  extractClaim(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return  (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(HashMap<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()*604800000))  //refreshToken valid for 7 days
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
