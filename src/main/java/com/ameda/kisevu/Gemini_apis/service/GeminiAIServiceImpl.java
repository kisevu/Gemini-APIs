package com.ameda.kisevu.Gemini_apis.service;/*
*
@author ameda
@project Gemini-apis
@
*
*/

import com.ameda.kisevu.Gemini_apis.DTO.AllModelsResponse;
import com.ameda.kisevu.Gemini_apis.DTO.GeminiRequest;
import com.ameda.kisevu.Gemini_apis.DTO.GeminiResponse;
import com.ameda.kisevu.Gemini_apis.constants.AppConstants;
import com.ameda.kisevu.Gemini_apis.exceptions.ResourceNotFoundExceptions;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class GeminiAIServiceImpl implements GeminiAIService{
    private final AppConstants appConstants;
    private final RestTemplate restTemplate;

    private String res="";

    /*
   @author kisevu
   */
    @Override
    public String queryString(String query) {
        String apiKey = appConstants.getApiKey();
        String apiUrl = appConstants.getApiUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("prompt", query);
        requestBody.put("max_tokens", 2); // Adjust max_tokens as needed

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        int retries = 0;
        final int MAX_RETRIES = 5; // Adjust as needed
        final int BACKOFF_BASE_TIME = 1000; // Base time for exponential backoff in milliseconds

        while (retries < MAX_RETRIES) {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
                JSONObject jsonResponse = new JSONObject(response.getBody());
                return jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text");
            } catch (HttpClientErrorException.TooManyRequests e) {
                int retryAfter = getRetryAfter(e); // Custom method to extract retry-after header
                long backoffTime = (long) (BACKOFF_BASE_TIME * Math.pow(2, retries)); // Exponential backoff

                if (retryAfter > 0) {
                    backoffTime = retryAfter * 1000L; // Use Retry-After header if available
                }

                try {
                    Thread.sleep(backoffTime);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during backoff", ex);
                }

                retries++;
            }
        }

        throw new RuntimeException("Max retries exceeded for OpenAI API request.");
    }

    private int getRetryAfter(HttpClientErrorException.TooManyRequests e) {
        HttpHeaders headers = e.getResponseHeaders();
        if (headers != null && headers.containsKey("Retry-After")) {
            String retryAfter = headers.getFirst("Retry-After");
            try {
                return Integer.parseInt(retryAfter);
            } catch (NumberFormatException ex) {
                // Handle the case where Retry-After is not a valid integer
                return 0;
            }
        }
        return 0; // Default to zero if Retry-After header is not present
    }



    /*
   @author kisevu
   */
    @Override
    public String result() {
        return res;
    }

    /*
   @author ameda
   */
    @Override
    public AllModelsResponse allModels() {
        String url = appConstants.getApiUrl();
        String apiKey = appConstants.getApiKey();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<AllModelsResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<AllModelsResponse>() {});
        System.out.println(response.getBody());
        return response.getBody();
    }


}
