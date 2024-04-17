package com.app.blog.service;

import java.util.List;


import com.app.blog.payloads.PostDto;
import com.app.blog.payloads.PostResponse;

public interface PostService {
	
	
	//Creating a post
	PostDto createPost(PostDto postDto ,Integer userId,Integer categoryId);
	
	//updating a post
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//Deleting a p single post
	void deletePost(Integer postId);
	
	//Getting all post
	PostResponse getAll(Integer pageNumber ,Integer pageSize,String sortBy,String sortDir);
	
	 
	//Getting post by id
	PostDto getPostById(Integer postId);
	
	//Getting all post by category
	PostResponse getAllPostByCategory(Integer categroyId,Integer pageNumber ,Integer pageSize);
	
	//Getting all post by user
	List<PostDto> getAllPostByUser(Integer userId);
	
	//search posts
	List<PostDto> searchPost(String keyword);
	
	
	
	

}
