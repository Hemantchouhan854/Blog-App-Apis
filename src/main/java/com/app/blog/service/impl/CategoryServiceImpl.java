package com.app.blog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.blog.entities.Category;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.CategoryDto;
import com.app.blog.repositories.CategoryRepo;
import com.app.blog.service.CategoryService;

@Service
public class CategoryServiceImpl  implements CategoryService{
	
	@Autowired	
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modleMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
	Category cat = this.modleMapper.map(categoryDto, Category.class);
	Category addcat = this.categoryRepo.save(cat);
		return this.modleMapper.map(addcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
	Category cat = this.categoryRepo.findById(categoryId)
			.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
	
	cat.setCategoryTitle(categoryDto.getCategoryTitle());
	cat.setCategoryDescription(categoryDto.getCategoryDescription());
	
	Category updateCategory = this.categoryRepo.save(cat);
	return this.modleMapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		this.categoryRepo.delete(cat);	
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		return this.modleMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> all = this.categoryRepo.findAll();
		List<CategoryDto> catdtos = all.stream().map((cat)->this.modleMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return catdtos;
	}

}
