package com.ritik.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor

@Setter
@Getter
public class UserDto {
	
	
	private int id;
	
	@Email(message = "Provided email is not valid !!")
	private String email;
	
	@NotEmpty
	@Size(min=3, max=10, message = "Password must be min of 3 chars and max of 10 chars")
	private String password;
	
	@NotEmpty(message = "About must not be empty")
	private String about;
	
	@NotEmpty
	@Size(min = 4, message = "Username must be greater than 3 characters !!")
	private String name;

}
