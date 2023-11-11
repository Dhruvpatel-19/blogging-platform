package com.example.BloggingPlatform.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
	
	
	public List<Comment> getAllComments(){
		return commentRepository.findAll();
	}
	
	public List<Comment> getAllCommentsOnBlogPost(String blogId){
		Optional<BlogPost> blogPost = blogPostRepository.findById(blogId);
		if(blogPost.get()!=null) {
			BlogPost blogPostObj = blogPost.get();
			List<Comment> comments = commentRepository.findAllByBlogPost(blogPostObj);
			return comments;
		}
		else {
			return null;
		}
	}
	
	public Comment addComment(Comment comment,String blogId) throws Exception {
		Optional<BlogPost> blogPost = blogPostRepository.findById(blogId);
		if(blogPost.get()!=null) {
			BlogPost blogPostObj = blogPost.get();
			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			comment.setCommenterName(currentUser);
			comment.setBlogPost(blogPostObj);
			comment.setCreationDate(LocalDate.now());
			return commentRepository.save(comment);
		}
		else {
			throw new Exception("Blog with give ID doesn't exists");
		}
	}
	
	
	public Comment updateComment(Comment updatedComment, String blogId, String commentId) throws Exception {
		Optional<BlogPost> blogPost = blogPostRepository.findById(blogId);
		if(blogPost.get()!=null) {
			
			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			
			Optional<Comment> comment = commentRepository.findById(commentId);
			
			if(comment.get()!= null) {
				Comment commentRepoObj = comment.get();
				
				if(currentUser.equals(commentRepoObj.getCommenterName())) {
					commentRepoObj.setCommentText(updatedComment.getCommentText());
					return commentRepository.save(commentRepoObj);
				}
				else {
					throw new Exception("Current user is not authorized to update this comment");
				}
			}
			else{
				throw new Exception("Comment can't be updated");
			}
			
		}
		else {
			throw new Exception( "Blog doesn't exist");
		}
	}
	
	public String deleteComment(String commentId) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.get()!= null) {
			Comment commentRepoObj = comment.get();
			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			if(currentUser.equals(commentRepoObj.getCommenterName())) {
				commentRepository.delete(commentRepoObj);
				return "Comment deleted Successfully";
			}
			else {
				return "Current user is not authorized to delete this comment";
			}
		}
		else{
			return "Comment doesn't exist;";
		}
			
	}
}
