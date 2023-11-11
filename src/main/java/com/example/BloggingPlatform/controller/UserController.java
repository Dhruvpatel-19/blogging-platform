package com.example.BloggingPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloggingPlatform.collections.User;
import com.example.BloggingPlatform.model.JwtRequest;
import com.example.BloggingPlatform.model.JwtResponse;
import com.example.BloggingPlatform.service.UserService;



@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signUp")
	public User signUp(@RequestBody User user) throws Exception {
		return userService.signUp(user);
	}
	
	@PostMapping("/signIn")
	public JwtResponse signIn(@RequestBody JwtRequest jwtRequest) throws Exception {
		return userService.signIn(jwtRequest);
	}
	
}
