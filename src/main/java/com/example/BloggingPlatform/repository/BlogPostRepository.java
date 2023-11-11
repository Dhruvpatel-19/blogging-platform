package com.example.BloggingPlatform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BloggingPlatform.collections.BlogPost;


public interface BlogPostRepository extends MongoRepository<BlogPost, String>{
}
