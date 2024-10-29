package com.ritik.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.blog.payloads.ApiResponse;
import com.ritik.blog.payloads.CommentDto;
import com.ritik.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// Post controller to create comments

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable(name = "postId") Integer id) {

		CommentDto comment = this.commentService.createComment(commentDto, id);

		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}

	// Delete controller to delete specific comment

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "commentId") Integer id) {

		this.commentService.deleteComment(id);

		return new ResponseEntity<>(new ApiResponse("Comment deleted successfully!", true), HttpStatus.OK);
	}

}
