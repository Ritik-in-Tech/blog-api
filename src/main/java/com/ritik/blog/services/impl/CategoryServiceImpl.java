package com.ritik.blog.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
//import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ritik.blog.entities.Category;
import com.ritik.blog.exceptions.ResourceNotFoundException;
import com.ritik.blog.payloads.CategoryDto;
import com.ritik.blog.repositories.CategoryRepo;
import com.ritik.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired  // It injects the dependencies of CategoryRepo in this CategoryService class
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category=this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory=this.categoryRepo.save(category);
		CategoryDto newCategoryDto=this.modelMapper.map(addedCategory, CategoryDto.class);
		return newCategoryDto;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		
		if(categoryDto.getCategoryTitle()!=null) {
			category.setCategoryTitle(categoryDto.getCategoryTitle());
		}
		
		if(categoryDto.getCategoryDescription()!=null) {
			category.setCategoryDescription(categoryDto.getCategoryDescription());
		}
		
		Category updatedCategory=this.categoryRepo.save(category);
		CategoryDto updatedCategoryDto=this.modelMapper.map(updatedCategory, CategoryDto.class);
//		Category updatedCategory=this.categoryRepo.
		return updatedCategoryDto;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));
		
		this.categoryRepo.delete(category);

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));
		
		CategoryDto categoryDto=this.modelMapper.map(category, CategoryDto.class);
		
		return categoryDto;
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		
		List<Category> allCategories=this.categoryRepo.findAll();
		
		 List<CategoryDto> allCategoryDtos = allCategories.stream()
			        .map(category -> this.modelMapper.map(category, CategoryDto.class))
			        .toList();
		
		return allCategoryDtos;
	}

}
