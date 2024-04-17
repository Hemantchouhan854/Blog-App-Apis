package com.app.blog.service;

import java.util.List;

import com.app.blog.entities.User;
import com.app.blog.payloads.UserDto;

import ch.qos.logback.core.model.INamedModel;

public interface UserService {
	
	UserDto registerNewUser(UserDto user);
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userid);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
