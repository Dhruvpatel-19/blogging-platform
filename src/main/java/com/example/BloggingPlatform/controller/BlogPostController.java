package com.example.BloggingPlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloggingPlatform.collections.BlogPost;
import com.example.BloggingPlatform.service.BlogPostService;

@RestController
public class BlogPostController {
	
	@Autowired
	private BlogPostService blogPostService;
	
	@GetMapping("/getBlogs")
	public List<BlogPost> getBlogs(){
		return blogPostService.getBlogs();
	}
	
	@PostMapping("/addBlog")
	public BlogPost addBlog(@RequestBody BlogPost blogPost) throws Exception {
		return blogPostService.addBlog(blogPost);
	}
	
	@PutMapping("/updateBlog/{blogId}")
	public BlogPost updateBlog(@RequestBody BlogPost blogPost, @PathVariable String blogId) throws Exception {
		return blogPostService.updateBlog(blogPost, blogId);
	}
	
	@DeleteMapping("/deleteBlog/{blogId}")
	public String deleteBlog(@PathVariable String blogId) {
		return blogPostService.deleteBlog(blogId);
	}
	
}
