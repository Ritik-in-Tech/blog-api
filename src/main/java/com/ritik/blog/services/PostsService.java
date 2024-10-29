package com.ritik.blog.services;

import java.util.List;

import com.ritik.blog.entities.Post;
import com.ritik.blog.payloads.PostResponse;
import com.ritik.blog.payloads.PostsDto;

public interface PostsService {
	
	// Create Post 
	
	PostsDto creatPost(PostsDto postsDto,Integer categoryId, Integer userId);
	
	// Update Post
	PostsDto updatePost(PostsDto postsDto, Integer postId);
	
	// Delete Post
	
	void deletePost(Integer postId);
	
	// get All Posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy);
	
	// get Posts By Id
	PostsDto getPostById(Integer postId);
	
	// get All Post By Category
	List<PostsDto> getAllPostByCategory(Integer categoryId);
	
	// get all Post By User
	List<PostsDto> getAllPostByUser(Integer userId);
	
	// search posts from the keywords
	List<PostsDto> getAllPostsByKeyword(String keyword);

}
