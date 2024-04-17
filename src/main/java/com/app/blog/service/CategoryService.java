package com.app.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.blog.payloads.CategoryDto;


public interface CategoryService {
	
	//Here we will define our all dao methods for the database operations
	
	
	//for creating the category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// for updating the category 
	CategoryDto updateCategory(CategoryDto categoryDto ,Integer categoryId);
	
	// for deleting the category
	void deleteCategory(Integer categoryId);
	
	//Getting category by id
	CategoryDto getCategory(Integer categoryId);
	
	//Getting all the category
	List<CategoryDto> getAllCategory();

}
