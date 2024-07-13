package com.ameda.kisevu.Gemini_apis.exceptions;/*
*
@author ameda
@project Gemini-apis
@
*
*/

public class ResourceNotFoundExceptions extends RuntimeException{
    public ResourceNotFoundExceptions() {
        super();
    }

    public ResourceNotFoundExceptions(String message) {
        super(message);
    }
}
