package com.ameda.kisevu.Gemini_apis.DTO;/*
*
@author ameda
@project Gemini-apis
@
*
*/

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllModelsResponse {
    private String object;
    private List<MyModel> data;

    @Override
    public String toString() {
        return "AllModelsResponse{" +
                "object='" + object + '\'' +
                ", data=" + data +
                '}';
    }
}
