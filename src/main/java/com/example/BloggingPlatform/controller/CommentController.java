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

import com.example.BloggingPlatform.collections.Comment;
import com.example.BloggingPlatform.service.CommentService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@GetMapping("/getAllComments")
	public List<Comment> getAllComments(){
		return commentService.getAllComments();
	}
	
	@GetMapping("/getAllComments/{blogId}")
	public List<Comment> getAllCommentsOnBlogPost(String blogId){
		return commentService.getAllComments();
	}
	
	@PostMapping("/addComment/{blogId}")
	public Comment addComment(@RequestBody Comment comment, @PathVariable String blogId) throws Exception {
		return commentService.addComment(comment, blogId);
	}
	
	@PutMapping("/updateComment/{blogId}/{commentId}")
	public Comment updateComment(@RequestBody Comment updatedComment, @PathVariable String blogId, 
			@PathVariable String commentId) throws Exception {
		return commentService.updateComment(updatedComment, blogId, commentId);
	}
	
	@DeleteMapping("/deleteComment/{commentId}")
	public String deleteComment(@PathVariable String commentId) {
		return commentService.deleteComment(commentId);
	}
}
