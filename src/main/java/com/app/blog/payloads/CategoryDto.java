package com.app.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	@NotEmpty
	@Size(min = 4 ,message = "Minimum size of the Title is 4 ")
	private String categoryTitle;
	@NotEmpty
	@Size(min = 5)
	private String categoryDescription;

}
