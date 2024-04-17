package com.app.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.blog.entities.Comments;

public interface CommentRepo  extends JpaRepository<Comments, Integer>{
	
	

}
