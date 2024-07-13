package com.ameda.kisevu.Gemini_apis.service;/*
*
@author ameda
@project Gemini-apis
*
*/

import org.springframework.security.core.userdetails.UserDetails;
import java.util.HashMap;

public interface JwtService {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateRefreshToken(HashMap<String, Object> extractClaims, UserDetails userDetails);
}
