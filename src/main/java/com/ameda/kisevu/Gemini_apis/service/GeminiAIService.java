package com.ameda.kisevu.Gemini_apis.service;/*
*
@author ameda
@project Gemini-apis
*
*/


import com.ameda.kisevu.Gemini_apis.DTO.AllModelsResponse;


public interface GeminiAIService {

    String queryString(String query);

    String result();

    AllModelsResponse allModels();
}
