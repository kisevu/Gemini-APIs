package com.ameda.kisevu.Gemini_apis.service.impl;/*
*
@author ameda
@project Gemini-apis
*
*/

import com.ameda.kisevu.Gemini_apis.DTO.JwtAuthResponse;
import com.ameda.kisevu.Gemini_apis.DTO.LoginRequest;
import com.ameda.kisevu.Gemini_apis.DTO.RefreshTokenRequest;
import com.ameda.kisevu.Gemini_apis.DTO.SignUpRequest;
import com.ameda.kisevu.Gemini_apis.entities.Role;
import com.ameda.kisevu.Gemini_apis.entities.User;
import com.ameda.kisevu.Gemini_apis.repositories.UserRepository;
import com.ameda.kisevu.Gemini_apis.service.AuthenticationService;
import com.ameda.kisevu.Gemini_apis.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User signUp(SignUpRequest request){
        User user = User.builder()
                .email(request.getEmail())
                .firstname(request.getFirstName())
                .secondname(request.getLastName())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        return  userRepository.save(user);
    }

    public JwtAuthResponse signIn(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthResponse authResponse = new JwtAuthResponse();
        authResponse.setToken(jwt);
        authResponse.setRefreshToken(refreshToken);
        return  authResponse;
    }

    public JwtAuthResponse refreshToken(RefreshTokenRequest request){
        String userEmail = jwtService.extractUserName(request.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(request.getToken(),user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthResponse authResponse = new JwtAuthResponse();
            authResponse.setToken(jwt);
            authResponse.setRefreshToken(request.getToken());
            return  authResponse;
        }
        return null;
    }
}
