package com.ritik.blog.payloads;

import java.util.Date;

import com.ritik.blog.entities.Category;
import com.ritik.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostsDto {
	
	private Integer post_id;
	
	private String title;
	
	private String content;
	
	private String imageUrl;
	
	private Date addeddDate;
	
	private CategoryDto category;
	
	private UserDto user;

}
