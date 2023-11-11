package com.example.BloggingPlatform.collections;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class Comment {
	 @Id
	 private String id;
	 private String commenterName;
	 private String commentText;
	 private LocalDate creationDate;
	 @DBRef
	 private BlogPost blogPost;
	 
	 public Comment() {
		 
	 }
	 
	 public Comment(String commenterName, String commentText, LocalDate creationDate, BlogPost blogPost) {
	     this.commenterName = commenterName;
	     this.commentText = commentText;
	     this.creationDate = creationDate;
	     this.blogPost = blogPost;
	 }
	 
	 public Comment(String id, String commenterName, String commentText, LocalDate creationDate, BlogPost blogPost) {
	     this.id = id;
	     this.commenterName = commenterName;
	     this.commentText = commentText;
	     this.creationDate = creationDate;
	     this.blogPost = blogPost;
	 }

	 public String getId() {
	    return id;
	 }

	 public void setId(String id) {
	    this.id = id;
	 }

	 public String getCommenterName() {
	     return commenterName;
	 }

	 public void setCommenterName(String commenterName) {
	     this.commenterName = commenterName;
	 }

	 public String getCommentText() {
	     return commentText;
	 }

	 public void setCommentText(String commentText) {
	     this.commentText = commentText;
	  }

	 public LocalDate getCreationDate() {
	     return creationDate;
	   }

	 public void setCreationDate(LocalDate creationDate) {
	     this.creationDate = creationDate;
	 }

	 public BlogPost getBlogPost() {
	     return blogPost;
	 }

	 public void setBlogPost(BlogPost blogPost) {
	     this.blogPost = blogPost;
	 }
}
