package com.ameda.kisevu.Gemini_apis.DTO;/*
*
@author ameda
@project Gemini-apis
@
*
*/

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyModel {
    private String id;
    private String object;
    private long created;  // Unix timestamp
    private String ownedBy;

    @Override
    public String toString() {
        return "MyModel{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
