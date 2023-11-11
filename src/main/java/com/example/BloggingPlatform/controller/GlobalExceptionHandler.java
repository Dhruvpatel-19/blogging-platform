package com.example.BloggingPlatform.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
    public final String handleException(Exception exception) {     
        return "Exception with message : "+exception.getMessage();
    }
	
	@ExceptionHandler(value = RuntimeException.class)
    public final String handleRuntimeException(RuntimeException exception) {     
        return "Exception with message : "+exception.getMessage();
    }
	
	@ExceptionHandler(value = IOException.class)
    public final String handleIOExcepion(IOException exception) {     
        return "Exception with message : "+exception.getMessage();
    }
}