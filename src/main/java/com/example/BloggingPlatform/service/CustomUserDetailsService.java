package com.example.BloggingPlatform.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.BloggingPlatform.collections.User;
import com.example.BloggingPlatform.repository.UserRepository;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		if(username == null) {
			throw new UsernameNotFoundException("User not found!!");
		}
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username '" + username + "' not found"));
		//return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}
