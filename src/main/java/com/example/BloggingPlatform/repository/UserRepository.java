package com.example.BloggingPlatform.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BloggingPlatform.collections.User;

public interface UserRepository extends MongoRepository<User, String>{

	Optional<User> findByUsername(String username);
	
}
