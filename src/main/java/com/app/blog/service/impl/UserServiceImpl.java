package com.app.blog.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.blog.config.AppContants;
import com.app.blog.entities.Role;
import com.app.blog.entities.User;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.UserDto;
import com.app.blog.repositories.RoleRepo;
import com.app.blog.repositories.UserRepo;
import com.app.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userdto) {
		User user = this.dtoTOUser(userdto);
		User savedUser = userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userid) {
		
		User user = this.userRepo.findById(userid)
				.orElseThrow(()->new ResourceNotFoundException("user","id",userid));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = userRepo.save(user);
		UserDto userToDto1 = this.userToDto(updatedUser);
		
		return userToDto1;
		
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("user", "id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		 List<User> users= this.userRepo.findAll();
		List<UserDto> listUserDto = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return listUserDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id", userId));
		this.userRepo.delete(user);
	}
	
	private User dtoTOUser(UserDto userDto) {
		//Here we are doing the conversion using the ModelMapper
		
		User user =this.modelMapper.map(userDto, User.class);
		
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		user.setEmail(userDto.getEmail());
		
		return user;
		
	}
	
	private UserDto userToDto(User user) {
		
		UserDto userDto =this.modelMapper.map(user, UserDto.class);
		
//		UserDto userDto=new UserDto();
//		userDto.setName(user.getName());
//		userDto.setId(user.getId());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
		
		
		return userDto;
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//Encoding the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//Setting up the role for the user>>>normal user
		Role role = this.roleRepo.findById(AppContants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
