package com.ameda.kisevu.Gemini_apis.DTO;/*
*
@author ameda
@project Gemini-apis
*
*/

import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
