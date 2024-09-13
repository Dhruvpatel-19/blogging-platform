package com.example.BloggingPlatform.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.BloggingPlatform.collections.BlogPost;
import com.example.BloggingPlatform.collections.Comment;
import com.example.BloggingPlatform.repository.BlogPostRepository;
import com.example.BloggingPlatform.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	
	// add a new comment to a specific blog post
    public String addComment(Comment comment, String blogId) throws Exception {
        Optional<BlogPost> blogPost = blogPostRepository.findById(blogId);
        if (blogPost.isPresent()) {
            BlogPost blogPostObj = blogPost.get();
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            comment.setCommenterName(currentUser);
            comment.setBlogPost(blogPostObj);
            comment.setCreationDate(LocalDate.now());
            commentRepository.save(comment);
            return "redirect:/getBlog/" + blogId;
        } else {
            throw new Exception("Blog with the given ID doesn't exist");
        }
    }

	
	// get comment by ID for updating, and checking current user's rights to update it
    public String showUpdateCommentForm(String commentId, Model model) throws Exception {    	
    	
    	Optional<Comment> comment = commentRepository.findById(commentId);
    	
    	if(comment.isPresent()) {
    		Comment commentFromRepo = comment.get();
    		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
    		
    		if (currentUser.equals(commentFromRepo.getCommenterName())) {
    			model.addAttribute("comment", comment.get());
    	        return "editComment";
    		}
    		else {
    			throw new Exception("Current user is not authorized to update this comment");
    		}
    	}
    	else {
    		throw new Exception("Comment with the given ID doesn't exist");
    	}
    	
    }
    
    
    // update an existing comment
    public String updateComment(Comment updatedComment){
    	Comment commentFromRepo = commentRepository.findById(updatedComment.getId()).orElse(null);
    	commentFromRepo.setCommentText(updatedComment.getCommentText());
        commentRepository.save(commentFromRepo);
        return "redirect:/getBlog/" + commentFromRepo.getBlogPost().getId(); 
    }   

    // delete existing comment
    public String deleteComment(String commentId) throws Exception {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            Comment commentFromRepo = commentOpt.get();
            
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            
            //check if the current user is the owner of the comment
            if (currentUser.equals(commentFromRepo.getCommenterName())) {
            	String blogId = commentFromRepo.getBlogPost().getId();
                commentRepository.delete(commentFromRepo);
                return "redirect:/getBlog/" + blogId; 
            } else {
            	throw new Exception("Current user is not authorized to delete this comment");
            }
        } else {
        	throw new Exception("Comment with given ID doesn't exist");
        }
    }

	
}
