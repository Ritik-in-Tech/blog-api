package com.ritik.blog.controllers;

import java.util.List;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.blog.payloads.ApiResponse;
//import com.ritik.blog.entities.User;
import com.ritik.blog.payloads.UserDto;
import com.ritik.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// POST - > create User
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		
		UserDto createUserDto=this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);	
	}
	
	// PUT -> update User
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") Integer uid)
	{
		UserDto updatedUserDto=this.userService.updateUser(userDto,uid);
		return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
	}
	
	// Delete -> delete User
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
	{
		this.userService.deleteUser(uid);
		return new ResponseEntity<>(new ApiResponse("User deleted successfully!", true),HttpStatus.OK);
	}
	
	
	// Get -> user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer uid)
	{
		UserDto getAUserDto=this.userService.getUserById(uid);
		return new ResponseEntity<>(getAUserDto,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		List<UserDto> getAllUsersDto=this.userService.getAllUsers();
		return new ResponseEntity<>(getAllUsersDto,HttpStatus.OK);
		
	}

}
