package com.example.BloggingPlatform.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.BloggingPlatform.Util.JwtUtil;
import com.example.BloggingPlatform.service.CustomUserDetailsService;

//as I have changed Spring Boot version from 3.1.5 to 2.5.4 
//due to WebSecurityConfigurerAdapter extension problem

//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get jwt
		//Bearer
		//validate
		//taking token from "Authorization" header
		//String requestTokenHeader =  request.getHeader("Authorization");
		
		//if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {
		//		jwtToken = requestTokenHeader.substring(7);
		//	}
		//	else {
		//		System.out.println("Token is not validated..");
		//	}
		
		
		//taking token from session
		//String requestTokenHeader = (String) request.getSession().getAttribute("JWT_TOKEN");
		
		String jwtToken = (String) request.getSession().getAttribute("JWT_TOKEN");
		String username = null;
		
		//null and format
		if(jwtToken != null){
						
			try {
				username = this.jwtUtil.extractUsername(jwtToken);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			//security
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Token is not validated..");
			}
		}
		filterChain.doFilter(request, response);	
	}
}
