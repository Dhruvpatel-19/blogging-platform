package com.example.BloggingPlatform.service;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BloggingPlatform.Util.JwtUtil;
import com.example.BloggingPlatform.collections.User;
import com.example.BloggingPlatform.model.JwtRequest;
import com.example.BloggingPlatform.model.JwtResponse;
import com.example.BloggingPlatform.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager  authenticationManager;
	
	public User signUp(User user) throws Exception {
		String pwd = user.getPassword();
		String encryptedPwd = passwordEncoder.encode(pwd);
		user.setPassword(encryptedPwd);
		user.setRegistrationDate(LocalDate.now());
		return userRepository.save(user);
	}
	
	
	public JwtResponse signIn(JwtRequest jwtRequest) throws Exception {
		
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
		}
		catch(UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Invalid Credential");
		}
		
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		
		String token = jwtUtil.generateToken(userDetails);
		return  new JwtResponse(token);
	}
}