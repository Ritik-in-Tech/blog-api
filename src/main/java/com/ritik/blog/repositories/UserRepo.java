package com.ritik.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritik.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
}
