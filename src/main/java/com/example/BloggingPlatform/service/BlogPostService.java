package com.example.BloggingPlatform.service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.BloggingPlatform.collections.BlogPost;
import com.example.BloggingPlatform.collections.User;
import com.example.BloggingPlatform.repository.BlogPostRepository;
import com.example.BloggingPlatform.repository.UserRepository;

@Service
public class BlogPostService {

	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<BlogPost> getBlogs() {
		List<BlogPost> blogPosts = blogPostRepository.findAll();
		return blogPosts;
	}
	
	public BlogPost addBlog(BlogPost blogPost) throws Exception {
		
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> user = userRepository.findByUsername(currentUser);
		
		if(user.get() == null)
			throw new Exception("User not valid");
		
		blogPost.setAuthor(user.get());
		blogPost.setCreationDate(LocalDate.now());
		return blogPostRepository.save(blogPost);
	}

	public BlogPost updateBlog(BlogPost blogPost, String blogId) throws Exception {
		Optional<BlogPost> blogPostFromRepo = blogPostRepository.findById(blogId);
		if(blogPostFromRepo.get()!=null) {
			BlogPost blogPostFromRepoObj = blogPostFromRepo.get();
			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			if(currentUser.equals(blogPostFromRepoObj.getAuthor().getUsername())) {
				blogPostFromRepoObj.setTitle(blogPost.getTitle());
				blogPostFromRepoObj.setContent(blogPost.getContent());
				blogPostFromRepoObj.setTags(blogPost.getTags());
				return blogPostRepository.save(blogPostFromRepoObj);
			}
			else {
				throw new Exception("Current user don't have right to update this blog");
			}
		}
		else {
			throw new Exception("Blog with given id doesn't exists successfully");
		}
	}
	
	public String deleteBlog(String blogId) {
		Optional<BlogPost> blogPostFromRepo = blogPostRepository.findById(blogId);
		if(blogPostFromRepo.get()!=null) {
			BlogPost blogPostFromRepoObj = blogPostFromRepo.get();
			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			if(currentUser.equals(blogPostFromRepoObj.getAuthor().getUsername())) {
				blogPostRepository.delete(blogPostFromRepoObj);
				return "Blog deleted successfully";
			}
			else {
				return "Current user don't have right to delete this blog";
			}
		}
		else {
			return "Blog with given id doesn't exists successfully";
		}
	}
	
	
}
