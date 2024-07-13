package com.ameda.kisevu.Gemini_apis.DTO;/*
*
@author ameda
@project Gemini-apis
@
*
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GeminiRequest {
    private String query;
}
