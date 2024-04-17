package com.app.blog.payloads;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.app.blog.entities.Comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	private String title;
	private String content;
	
	private String imageName;
	private LocalDate addedDate;
	
	private CategoryDto category;
	private UserDto user;
	
	private Set<CommentDto> comments=new HashSet<>();
	
	

}
