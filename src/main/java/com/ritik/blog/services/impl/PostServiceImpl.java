package com.ritik.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.query.Page;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ritik.blog.entities.Category;
import com.ritik.blog.entities.Post;
import com.ritik.blog.entities.User;
import com.ritik.blog.exceptions.ResourceNotFoundException;
import com.ritik.blog.payloads.PostResponse;
import com.ritik.blog.payloads.PostsDto;
import com.ritik.blog.repositories.CategoryRepo;
import com.ritik.blog.repositories.PostsRepo;
import com.ritik.blog.repositories.UserRepo;
import com.ritik.blog.services.PostsService;


@Service
public class PostServiceImpl implements PostsService {
	
	
	@Autowired
	private PostsRepo postsRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostsDto creatPost(PostsDto postsDto, Integer categoryId, Integer userId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post=this.modelMapper.map(postsDto, Post.class);
		post.setImageUrl("default.png");
		post.setAddeddDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost=this.postsRepo.save(post);
		
		return this.modelMapper.map(createdPost, PostsDto.class);
	}

	@Override
	public PostsDto updatePost(PostsDto postsDto, Integer postId) {
		// TODO Auto-generated method stub
		
        Post post=this.postsRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		
        if(postsDto.getTitle()!=null) {
        	post.setTitle(postsDto.getTitle());
        }
        
        if(postsDto.getImageUrl()!=null) {
        	post.setImageUrl(postsDto.getImageUrl());
        }
        
        if(postsDto.getContent()!=null) {
        	post.setContent(postsDto.getContent());
        }
        
        Post updatPost= this.postsRepo.save(post);
        
        PostsDto updatedPostsDto= this.modelMapper.map(updatPost, PostsDto.class);
        return updatedPostsDto;
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		
		Post post= this.postsRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		
		this.postsRepo.delete(post);

	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy) {
		// TODO Auto-generated method stub
		
//		int pageSize=5;	
//		int pageNumber=1;
		
		PageRequest p=PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).descending()); 
		
		org.springframework.data.domain.Page<Post> pagePostPage = this.postsRepo.findAll(p);
		
		List<Post> posts=pagePostPage.getContent();
		
//		List<Post> posts= this.postsRepo.findAll();
		List<PostsDto> aLLPostsDtos= posts.stream().map((post)-> this.modelMapper.map(post,PostsDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		
		postResponse.setContent(aLLPostsDtos);
		postResponse.setPageNumber(pagePostPage.getNumber());
		postResponse.setPageSize(pagePostPage.getSize());
		postResponse.setTotalElements(pagePostPage.getTotalElements());
		postResponse.setTotalPages(pagePostPage.getTotalPages());
		postResponse.setLastPage(pagePostPage.isLast());
		return postResponse;
	}

	@Override
	public PostsDto getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post= this.postsRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		return this.modelMapper.map(post, PostsDto.class);
	}

	@Override
	public List<PostsDto> getAllPostByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		
		List<Post> allPostsByCategoryList=this.postsRepo.findByCategory(category);
		
	    List<PostsDto> allPostsDtos=	allPostsByCategoryList.stream().map((post-> this.modelMapper.map(post, PostsDto.class))).collect(Collectors.toList());
		return allPostsDtos;
	}

	@Override
	public List<PostsDto> getAllPostByUser(Integer userId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		
		List<Post> allPostsByUserList=this.postsRepo.findByUser(user);
		
	    List<PostsDto> allPostsDtos=	allPostsByUserList.stream().map((posts)-> this.modelMapper.map(posts,PostsDto.class )).collect(Collectors.toList());
		
		return allPostsDtos;
	}

	@Override
	public List<PostsDto> getAllPostsByKeyword(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postsRepo.findByTitleContaining(keyword);
		List<PostsDto> postsDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostsDto.class)).collect(Collectors.toList());
		return postsDtos;
	}

}
