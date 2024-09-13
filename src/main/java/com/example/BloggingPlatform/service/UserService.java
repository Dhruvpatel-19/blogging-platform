package com.example.BloggingPlatform.service;

import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
		if(userRepository.findByUsername(user.getUsername()).isPresent())
			throw new Exception("User with username '"+user.getUsername()+"' already exists");
		String pwd = user.getPassword();
		String encryptedPwd = passwordEncoder.encode(pwd);
		user.setPassword(encryptedPwd);
		user.setRegistrationDate(LocalDate.now());
		return userRepository.save(user);
	}
	
	
	public String signIn(JwtRequest jwtRequest, HttpServletRequest request) throws Exception {
		
		Authentication authentication;
		try {
			authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
		}
		catch(UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Invalid Credential");
		}
		
		//setting authentication object in security context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//generating token
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = jwtUtil.generateToken(userDetails);
		
		HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        //adding jwt token using session
        request.getSession().setAttribute("JWT_TOKEN", token);

        return "redirect:/getAllBlogs";
	}
}