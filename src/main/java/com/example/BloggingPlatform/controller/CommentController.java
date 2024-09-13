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

import com.example.BloggingPlatform.collections.Comment;
import com.example.BloggingPlatform.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/addComment/{blogId}")
	public String addComment(@ModelAttribute Comment comment, @PathVariable String blogId) throws Exception {
		return commentService.addComment(comment, blogId);
	}

    //form for updating a comment
    @GetMapping("/updateComment/{id}")
    public String showUpdateCommentForm(@PathVariable String id, Model model) throws Exception {
       return commentService.showUpdateCommentForm(id, model);
    }

    //update form data will be processed
    @PostMapping("/updateComment/{id}")
    public String updateComment(@ModelAttribute Comment updatedComment){
        return commentService.updateComment(updatedComment);
    }

    @PostMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable String id, Model model) throws Exception {
        return commentService.deleteComment(id); 
    }
	
}
