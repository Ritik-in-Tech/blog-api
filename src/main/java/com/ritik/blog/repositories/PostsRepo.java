package com.ritik.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritik.blog.entities.Category;
import com.ritik.blog.entities.Post;
import com.ritik.blog.entities.User;

public interface PostsRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category); 
	
	List<Post> findByTitleContaining(String title);
}
