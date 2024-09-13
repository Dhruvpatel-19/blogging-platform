package com.example.BloggingPlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloggingPlatform.collections.BlogPost;
import com.example.BloggingPlatform.collections.Comment;
import com.example.BloggingPlatform.service.BlogPostService;
import com.example.BloggingPlatform.service.CommentService;

@Controller
public class BlogPostController {
	
	 @Autowired
	 private BlogPostService blogPostService;
	
	 @Autowired
	 private CommentService commentService;
	
	 @GetMapping("/getAllBlogs")
	 public String home(Model model) {
		 return blogPostService.getAllBlogs(model);
	    
	 }
	 
	 //to view blog
	 @GetMapping("/getBlog/{blogId}")
	 public String getBlog(@PathVariable String blogId, Model model) {
	     return blogPostService.getBlog(blogId, model);
	 }
	 
	 @GetMapping("/addBlog")
	 public String addBlogForm(Model model) {
	     model.addAttribute("blogPost", new BlogPost());
	     return "addblog";
	 }
	 
	 @PostMapping("/addBlog")
	 public String addBlog(@ModelAttribute BlogPost blogPost, Model model) throws Exception {
	     return blogPostService.addBlog(blogPost, model);
	 }
	 
	 //it will show form to update form else neccessary message
	 @GetMapping("/updateBlog/{id}")
	 public String updateBlogForm(@PathVariable String id, Model model) throws Exception {
	    return blogPostService.updateBlogForm(id, model);
	 }

	 //form data will be processed
	 @PostMapping("/updateBlog")
	 public String updateBlog(@ModelAttribute BlogPost blogPost){
	    blogPostService.updateBlog(blogPost);
	    return "redirect:/getAllBlogs";
	 }
	 
	 @PostMapping("/deleteBlog/{id}")
	 public String deleteBlog(@PathVariable String id, Model model) throws Exception {
		 return blogPostService.deleteBlog(id, model);
	 }

	
}
