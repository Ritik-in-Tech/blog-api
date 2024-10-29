package com.ritik.blog.controllers;

//import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ritik.blog.config.AppConstants;
//import com.ritik.blog.entities.Post;
import com.ritik.blog.payloads.ApiResponse;
import com.ritik.blog.payloads.PostResponse;
import com.ritik.blog.payloads.PostsDto;
import com.ritik.blog.services.FileService;
import com.ritik.blog.services.PostsService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostsController {

	@Autowired
	private PostsService postsService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// Post Method to create post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostsDto> createPost(@Valid @RequestBody PostsDto postsDto,
			@PathVariable(name = "userId") Integer uId, @PathVariable(name = "categoryId") Integer catId) {

		PostsDto newPostsDto = this.postsService.creatPost(postsDto, catId, uId);

		return new ResponseEntity<>(newPostsDto, HttpStatus.CREATED);
	}

	// Get method to get the posts by userId

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostsDto>> getPostsByUserId(@PathVariable(name = "userId") Integer uid) {

		List<PostsDto> allPostsDtos = this.postsService.getAllPostByUser(uid);

		return new ResponseEntity<>(allPostsDtos, HttpStatus.OK);

	}

	// GET MEthod to get the posts by category Id

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostsDto>> getPostsByCategoryId(@PathVariable(name = "categoryId") Integer catId) {

		List<PostsDto> allPostsByCategoryList = this.postsService.getAllPostByCategory(catId);

		return new ResponseEntity<>(allPostsByCategoryList, HttpStatus.OK);
	}

	// Get method to get the posts by postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostsDto> getPostByPostId(@PathVariable(name = "postId") Integer pId) {
		PostsDto postsDto = this.postsService.getPostById(pId);
		return new ResponseEntity<>(postsDto, HttpStatus.OK);
	}

	// Get method to get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sBy) {
		PostResponse allPosts = this.postsService.getAllPosts(pageNo, pSize, sBy);
		return new ResponseEntity<>(allPosts, HttpStatus.OK);
	}

	// Delete the post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "postId") Integer pId) {
		this.postsService.deletePost(pId);

		return new ResponseEntity<>(new ApiResponse("Post deletd successfully!", true), HttpStatus.OK);
	}

	@PostMapping("/posts/{postId}")
	public ResponseEntity<PostsDto> updatePost(@Valid @RequestBody PostsDto postsDto,
			@PathVariable(name = "postId") Integer pId) {
		PostsDto updatedPostsDto = this.postsService.updatePost(postsDto, pId);
		return new ResponseEntity<>(updatedPostsDto, HttpStatus.OK);
	}

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostsDto>> searchPostsByKeywords(@PathVariable(name = "keywords") String key) {

		List<PostsDto> allPostsByKeyword = this.postsService.getAllPostsByKeyword(key);
		return new ResponseEntity<>(allPostsByKeyword, HttpStatus.OK);

	}

	// Post Image Upload

	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostsDto> uploadPostImage(@PathVariable(name = "postId") Integer id,
			@RequestParam("image") MultipartFile image) {

		PostsDto postDto = this.postsService.getPostById(id);

		String fileName = null;
		try {
			fileName = this.fileService.uploadImage(path, image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		postDto.setImageUrl(fileName);

		PostsDto updatePost = this.postsService.updatePost(postDto, id);

		return new ResponseEntity<PostsDto>(updatePost, HttpStatus.OK);

	}

	// Image serve controller

	@GetMapping(value = "/posts/image/{imageName}", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String image, HttpServletResponse response)
			throws IOException {

		InputStream resource = this.fileService.getResource(path, image);

		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);

		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());

	}

}
