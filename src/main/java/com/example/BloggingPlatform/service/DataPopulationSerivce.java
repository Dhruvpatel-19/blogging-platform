package com.example.BloggingPlatform.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BloggingPlatform.collections.BlogPost;
import com.example.BloggingPlatform.collections.Comment;
import com.example.BloggingPlatform.collections.User;
import com.example.BloggingPlatform.repository.BlogPostRepository;
import com.example.BloggingPlatform.repository.CommentRepository;
import com.example.BloggingPlatform.repository.UserRepository;

@Service
public class DataPopulationSerivce {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public String populateData() {
		User user1 = new User("user1", "user1@gmail.com", passwordEncoder.encode("pass"), LocalDate.now());
		User user2 = new User("user2", "user2@gmail.com", passwordEncoder.encode("pass"), LocalDate.now());
		
		BlogPost blogPost1 = new BlogPost("Blog XYZ Title", "Conent", 
				user1, LocalDate.now(), Arrays.asList("Tag1", "Tag2", "Tag3"));
		
		Comment comment1 = new Comment(user1.getUsername(), "Comment Text by author", LocalDate.now(), blogPost1);
		Comment comment2 = new Comment(user2.getUsername(), "Comment Text", LocalDate.now(), blogPost1);
		
		userRepository.save(user1);
		userRepository.save(user2);
		blogPostRepository.save(blogPost1);
		commentRepository.save(comment1);
		commentRepository.save(comment2);
		
		return "Data Populated Successfully";
	}
}
