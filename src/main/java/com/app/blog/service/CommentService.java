package com.app.blog.service;

import com.app.blog.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
	
	void deleteComment(Integer commentId);	

}
