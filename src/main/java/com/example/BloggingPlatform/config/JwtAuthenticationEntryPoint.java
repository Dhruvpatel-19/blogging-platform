package com.example.BloggingPlatform.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	  @Override 
	  public void commence(HttpServletRequest request, HttpServletResponse response, 
			 AuthenticationException authException) throws IOException, ServletException { 
		  // TODO Auto-generated method stub
		  //response.sendError(401, "Unauthorized");
		  
		  //to redirect user to login page with an error message
	      String redirectUrl = "/signIn?signInErrorMsg=signIn_first";
	      response.sendRedirect(redirectUrl);
	 }
	 
}