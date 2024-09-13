package com.example.BloggingPlatform.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.BloggingPlatform.collections.BlogPost;
import com.example.BloggingPlatform.collections.Comment;
import com.example.BloggingPlatform.collections.User;
import com.example.BloggingPlatform.repository.BlogPostRepository;
import com.example.BloggingPlatform.repository.CommentRepository;
import com.example.BloggingPlatform.repository.UserRepository;

@Service
public class BlogPostService {

	@Autowired
	private BlogPostRepository blogPostRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	

	//home page
	public String getAllBlogs(Model model) {
		 List<BlogPost> blogPosts =  blogPostRepository.findAll();
	     model.addAttribute("blogPosts", blogPosts);
	     return "home";
	}
	
	
	public String getBlog(String blogId, Model model) {
	     Optional<BlogPost> blogPost = blogPostRepository.findById(blogId);
	     if(blogPost.isPresent()) {
		     List<Comment> comments =  commentRepository.findAllByBlogPost(blogPost.get());
		     model.addAttribute("blogPost", blogPost.get());
		     model.addAttribute("comments", comments);
		     return "blogDetails";
	     }
	     else {
	    	 List<BlogPost> blogPosts =  blogPostRepository.findAll();
		     model.addAttribute("blogPosts", blogPosts);
		     model.addAttribute("msg", "Blogpost you were requesting is not available");
		     return "home";
	     }
	}
	
	public String addBlog(BlogPost blogPost, Model model) throws Exception {
		
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<User> user = userRepository.findByUsername(currentUser);

		if(user.isEmpty())
			throw new Exception("User not valid");
		
		blogPost.setAuthor(user.get());
		blogPost.setCreationDate(LocalDate.now());
		blogPostRepository.save(blogPost);
		
		List<BlogPost> blogPosts =  blogPostRepository.findAll();
	    model.addAttribute("blogPosts", blogPosts);
	    model.addAttribute("msg", "Your blog has been added successfully");
	    return "home";
	}
	
	
	public String updateBlogForm(@PathVariable String id, Model model) throws Exception {
		Optional<BlogPost> blogPost = blogPostRepository.findById(id);
		 
		 if(blogPost.isPresent()) {
			 
			 //to check valid currentUser has access to update blog
			 String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			 if(currentUser.equals(blogPost.get().getAuthor().getUsername())){
				 model.addAttribute("blogPost", blogPost.get());
				 return "updateBlog";
			 }
			 else {
				 
				 //it will show alert				 
				 //List<BlogPost> blogPosts =  blogPostRepository.findAll();
			     //model.addAttribute("blogPosts", blogPosts);
			     //model.addAttribute("msg", "You don't have acess to update this blog");
			     //return "home";
				 
			     //this will work with controller advice
			     throw new Exception("You don't have acess to update this blog");
			 }
			 
			
		 }
		 else {
			 List<BlogPost> blogPosts =  blogPostRepository.findAll();
		     model.addAttribute("blogPosts", blogPosts);
		     model.addAttribute("msg", "Blogpost you were requesting to update is not available");
		     return "home";
		 }
		 
	 }
	 
	 
	//here not checking by id and rights of user, as it is a post request user will be able to make after first get request 
	public BlogPost updateBlog(BlogPost blogPost){
		//retrieving object from repo, so new instance is not stored in db
		BlogPost blogPostFromRepo = blogPostRepository.findById(blogPost.getId()).orElse(null);
		blogPostFromRepo.setTitle(blogPost.getTitle());
		blogPostFromRepo.setContent(blogPost.getContent());
		//blogPostFromRepo.setTags(blogPost.getTags());  //it was removing field from db because of null as(not in modelattribute)
		blogPostFromRepo.setTags(new ArrayList<String>());
		return blogPostRepository.save(blogPostFromRepo);
	}
	
	
	public String deleteBlog(String blogId, Model model) throws Exception {
		Optional<BlogPost> blogpost = blogPostRepository.findById(blogId);
		if(blogpost.isPresent()) {

			//check if user has right or not	
			String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
			if(currentUser.equals(blogpost.get().getAuthor().getUsername())){
				blogPostRepository.delete(blogpost.get());
				List<BlogPost> blogPosts =  blogPostRepository.findAll();
			    model.addAttribute("blogPosts", blogPosts);
			    model.addAttribute("msg", "Blogpost deleted successfully");
			    return "home";
			}
			else {
				throw new Exception("You don't have acess to delete this blog");
			}
			
		}
		else {
			List<BlogPost> blogPosts =  blogPostRepository.findAll();
		    model.addAttribute("blogPosts", blogPosts);
		    model.addAttribute("msg", "Blogpost you were requesting to delete is not available");
		    return "home";
		}
	}
	
	
	
	
}
