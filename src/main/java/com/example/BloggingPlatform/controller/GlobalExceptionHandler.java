package com.example.BloggingPlatform.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.BloggingPlatform.collections.BlogPost;
import com.example.BloggingPlatform.repository.BlogPostRepository;

/*
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
}*/

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@ExceptionHandler(value = Exception.class)
    public final String handleException(Exception exception, Model model) {   
		List<BlogPost> blogPosts =  blogPostRepository.findAll();
        String errorMsg =  "Exception with message : "+exception.getMessage();
        
        model.addAttribute("blogPosts", blogPosts);
        model.addAttribute("errorMsg", errorMsg);
        return "home";
    }
	
	@ExceptionHandler(value = RuntimeException.class)
    public final String handleRuntimeException(RuntimeException exception, Model model) {     
		List<BlogPost> blogPosts =  blogPostRepository.findAll();
        String errorMsg =  "Exception with message : "+exception.getMessage();
        
        model.addAttribute("blogPosts", blogPosts);
        model.addAttribute("errorMsg", errorMsg);
        return "home";
    }
	
	@ExceptionHandler(value = IOException.class)
    public final String handleIOExcepion(IOException exception, Model model) {     
		List<BlogPost> blogPosts =  blogPostRepository.findAll();
        String errorMsg =  "Exception with message : "+exception.getMessage();
        
        model.addAttribute("blogPosts", blogPosts);
        model.addAttribute("errorMsg", errorMsg);
        return "home";
    }
}