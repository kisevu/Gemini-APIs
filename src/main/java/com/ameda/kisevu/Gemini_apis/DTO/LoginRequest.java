package com.ameda.kisevu.Gemini_apis.DTO;/*
*
@author ameda
@project Gemini-apis
*
*/

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
