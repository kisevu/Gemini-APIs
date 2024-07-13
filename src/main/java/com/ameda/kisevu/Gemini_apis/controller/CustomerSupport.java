package com.ameda.kisevu.Gemini_apis.controller;/*
*
@author ameda
@project Gemini-apis
@
*
*/

import com.ameda.kisevu.Gemini_apis.DTO.AllModelsResponse;
import com.ameda.kisevu.Gemini_apis.constants.AppConstants;
import com.ameda.kisevu.Gemini_apis.exceptions.ResourceNotFoundExceptions;
import com.ameda.kisevu.Gemini_apis.service.GeminiAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class CustomerSupport {

    private final GeminiAIService geminiAIService;
    private final AppConstants appConstants;

    public CustomerSupport(GeminiAIService geminiAIService,
                           AppConstants appConstants) {
        this.geminiAIService = geminiAIService;
        this.appConstants = appConstants;
    }


    @GetMapping("/")
    public String customerWindow(){
        return "index";
    }

    @GetMapping("/models")
    public String allModels(Model model){
        AllModelsResponse response = geminiAIService.allModels();
        model.addAttribute("models",response.getData());
        return "models";
    }


    @PostMapping("/quiz")
    public String query(@RequestParam("query") String query, Model model){
        Map<String,Object> request = new HashMap<>();
        request.put("query",query);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(appConstants.getApiKey());
//        HttpEntity<Map<String,Object>> entity =
        try{
            String response = geminiAIService.queryString(query);
            model.addAttribute("query",query);
            model.addAttribute("response",response);
            return "result";
        }catch (ResourceNotFoundExceptions ex){
            log.error("Resource not retrievable: {}",ex.getMessage());
        }
        return null;
    }

    @GetMapping("/quiz")
    public String  result(Model model){
        ModelAndView mav = new ModelAndView();
        geminiAIService.result();
        return "quiz";
    }
}
