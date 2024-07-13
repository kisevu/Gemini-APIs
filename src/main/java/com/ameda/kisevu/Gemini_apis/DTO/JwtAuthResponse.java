package com.ameda.kisevu.Gemini_apis.DTO;/*
*
@author ameda
@project Gemini-apis
*
*/

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
    private String refreshToken;
}
