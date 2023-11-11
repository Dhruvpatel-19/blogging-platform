package com.example.BloggingPlatform.collections;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blogposts")
public class BlogPost {
	@Id
    private String id;
    private String title;
    private String content;
    @DBRef
    private User author;
    private LocalDate creationDate;
    private List<String> tags;

    public BlogPost() {
    }
    
    public BlogPost(String title, String content, User author, LocalDate creationDate, List<String> tags){
        this.title = title;
        this.content = content;
        this.author = author;
        this.creationDate = creationDate;
        this.tags = tags;
    }
    
    public BlogPost(String id, String title, String content, User author, LocalDate creationDate, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.creationDate = creationDate;
        this.tags = tags;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
}
