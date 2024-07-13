package com.ameda.kisevu.Gemini_apis.constants;/*
*
@author ameda
@project Gemini-apis
@
*
*/

import com.ameda.kisevu.Gemini_apis.config.GeminiConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppConstants {
    private final GeminiConfiguration geminiConfiguration;
    public String getApiUrl(){
        return  geminiConfiguration.getBaseUrl();
    }

    public String getApiKey(){
        return geminiConfiguration.getApiKey();
    }

}
