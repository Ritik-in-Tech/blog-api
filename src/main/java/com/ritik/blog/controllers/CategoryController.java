package com.ritik.blog.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.blog.payloads.ApiResponse;
import com.ritik.blog.payloads.CategoryDto;
import com.ritik.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	// POST API create categories
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createdCategoryDto=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createdCategoryDto,HttpStatus.CREATED);
		
	}
	
	
	// PUT API to update categories
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory( @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer catId)
	{
		CategoryDto updateCategoryDto=this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<>(updateCategoryDto,HttpStatus.OK);
	}
	
	
	// GET API to get category by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer catId)
	{
		CategoryDto getCategoryByIDto=this.categoryService.getCategoryById(catId);
		return new ResponseEntity<>(getCategoryByIDto,HttpStatus.OK);
	}
	
	
	// GET API to get all categories
	@GetMapping("")
	public ResponseEntity<List<CategoryDto>> getAllCategories()
	{
		List<CategoryDto> allCategoryDtos=this.categoryService.getAllCategories();	
		return new ResponseEntity<>(allCategoryDtos,HttpStatus.OK);
	}
	
	
	// DELETE API to delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer catId)
	{
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<>(new ApiResponse("Category deleted successfull!",true),HttpStatus.OK);
	}
	

}
