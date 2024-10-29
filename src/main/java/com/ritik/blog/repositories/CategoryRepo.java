package com.ritik.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritik.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
}
