package com.app.blog.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.entities.Role;
import com.app.blog.payloads.ApiResponse;
import com.app.blog.payloads.RoleDto;
import com.app.blog.payloads.UserDto;
import com.app.blog.service.UserService;

import jakarta.validation.Valid;
import lombok.val;
import lombok.experimental.PackagePrivate;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder  passwordEncoder;
	

	// Post-Creating the new user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto) {
		String password = userDto.getPassword();
		String encodepassword = this.passwordEncoder.encode(password);
		userDto.setPassword(encodepassword);
		
		
		UserDto createUserDto = this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	// Put - updating the user

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
			String password = userDto.getPassword();
			String encodepassword = this.passwordEncoder.encode(password);
			userDto.setPassword(encodepassword);
		UserDto updateUser = this.userService.updateUser(userDto, userId);
			return ResponseEntity.ok(updateUser);
	}
	
	//Delete-  Deleting the user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<UserDto> deleteuser(@Valid @PathVariable Integer userId){
		this.userService.deleteUser(userId);
		return  new ResponseEntity(new ApiResponse("User deleted successfully",true),HttpStatus.OK);	
	}
	
	//Get- getting the single user
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@Valid @PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));	
	}
	
	//Get -getting all user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>>getAllUser(){
		List<UserDto> allUsers = this.userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
		
	}

}
