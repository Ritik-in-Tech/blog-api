package com.ritik.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ritik.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
