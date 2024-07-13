package com.ameda.kisevu.Gemini_apis.controller;/*
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
import com.ameda.kisevu.Gemini_apis.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(authenticationService. signUp(request));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
