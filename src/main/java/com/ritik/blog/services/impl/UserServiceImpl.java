package com.ritik.blog.services.impl;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ritik.blog.entities.User;
import com.ritik.blog.exceptions.ResourceNotFoundException;
import com.ritik.blog.payloads.UserDto;
import com.ritik.blog.repositories.UserRepo;
import com.ritik.blog.services.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto user) {
		User newUser=this.DtoToUser(user);
		User savedUser = this.userRepo.save(newUser);
		return this.UserToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		
		 	if (userDto.getName() != null) {
		        user.setName(userDto.getName());
		    }
		    if (userDto.getEmail() != null) {
		        user.setEmail(userDto.getEmail());
		    }
		    if (userDto.getPassword() != null) {
		        user.setPassword(userDto.getPassword());
		    }
		    if (userDto.getAbout() != null) {
		        user.setAbout(userDto.getAbout());
		    }
		
		
		User updatedUser=this.userRepo.save(user);
		UserDto updatedUserDto=this.UserToDto(updatedUser);
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		 
		return this.UserToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> allUsers =  this.userRepo.findAll();
 		
        List<UserDto> allUserDtos=allUsers.stream().map(user-> this.UserToDto(user)).collect(Collectors.toList());
		 
		return allUserDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user=  this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		this.userRepo.delete(user);

	}
	
	private User DtoToUser(UserDto userDto) {
//		User newUser=new User();
		
//		newUser.setId(user.getId());
//		newUser.setName(user.getName());
//		newUser.setEmail(user.getEmail());
//		newUser.setPassword(user.getPassword());
//		newUser.setAbout(user.getAbout());
		
		
		 User user=this.modelMapper.map(userDto, User.class);
		
		
		return user;
	}
	
	public UserDto UserToDto(User user) {
//		UserDto newUserDto=new UserDto();
//		newUserDto.setId(user.getId());
//		newUserDto.setAbout(user.getAbout());
//		newUserDto.setEmail(user.getEmail());
//		newUserDto.setPassword(user.getPassword());
//		newUserDto.setName(user.getName());
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
	}
	

}
