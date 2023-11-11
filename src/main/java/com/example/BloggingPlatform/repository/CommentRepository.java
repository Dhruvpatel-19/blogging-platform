package com.example.BloggingPlatform.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BloggingPlatform.collections.BlogPost;
import com.example.BloggingPlatform.collections.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>{

	List<Comment> findAllByBlogPost(BlogPost blogPostObj);

}
