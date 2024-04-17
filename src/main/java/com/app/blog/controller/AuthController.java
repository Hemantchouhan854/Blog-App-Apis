package com.app.blog.controller;

import java.security.Principal;
import java.util.Optional;

import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.blog.entities.User;
import com.app.blog.exceptions.ApiException;
import com.app.blog.payloads.JwtAuthRequest;
import com.app.blog.payloads.JwtAuthResponse;
import com.app.blog.payloads.UserDto;
import com.app.blog.repositories.UserRepo;
import com.app.blog.security.JwtTokenHelper;
import com.app.blog.service.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		// System.out.println("INSIDE CREATE TOKEN FUNCTION => " +
		// request.getPassword());
		this.authenticate(request.getUsername(), request.getPassword());
		// System.out.println(request.getPassword()+" "+request.getUserName());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		// System.out.println(">>> TOKEN => " + token);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			System.out.println("Inside the authentication token");
			this.authenticationManager.authenticate(authenticationToken);
			System.out.println("REACHED => authenticationToken => " + authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("MY KEY => Invalid Details !! => " + e);
			throw new ApiException("Invalid username or password !!");
		}

	}

	// Register new user api

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

	// get loggedin user data
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/current-user/")
	public ResponseEntity<UserDto> getUser(Principal principal) {
		if(principal==null) {
			return new  ResponseEntity<UserDto>(HttpStatus.UNAUTHORIZED);
		}
		User user = this.userRepo.findByEmail(principal.getName()).get();

		return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
	}

}