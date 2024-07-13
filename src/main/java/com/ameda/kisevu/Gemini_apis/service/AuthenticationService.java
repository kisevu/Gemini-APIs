package com.ameda.kisevu.Gemini_apis.service;/*
*
@author ameda
@project Gemini-apis
*
*/

import com.ameda.kisevu.Gemini_apis.DTO.JwtAuthResponse;
import com.ameda.kisevu.Gemini_apis.DTO.LoginRequest;
import com.ameda.kisevu.Gemini_apis.DTO.RefreshTokenRequest;
import com.ameda.kisevu.Gemini_apis.DTO.SignUpRequest;
import com.ameda.kisevu.Gemini_apis.entities.User;

public interface AuthenticationService {

    User signUp(SignUpRequest request);
    JwtAuthResponse signIn(LoginRequest request);
    JwtAuthResponse refreshToken(RefreshTokenRequest request);
}
