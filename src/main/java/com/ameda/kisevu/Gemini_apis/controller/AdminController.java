package com.ameda.kisevu.Gemini_apis.controller;/*
*
@author ameda
@project Gemini-apis
*
*/


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {


    @GetMapping
    public ResponseEntity<String> message(){
        return  ResponseEntity.ok("Hi, Admin");
    }
}
