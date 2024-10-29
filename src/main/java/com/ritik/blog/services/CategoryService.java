package com.ritik.blog.services;

import java.util.List;

import com.ritik.blog.payloads.CategoryDto;

public interface CategoryService {
	
	// create
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// update
	
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	// delete
	
	void deleteCategory(Integer categoryId);
	
	// getACategory
	
	CategoryDto getCategoryById(Integer categoryId);
	
	// getAllCategory
	
	List<CategoryDto> getAllCategories();

}
