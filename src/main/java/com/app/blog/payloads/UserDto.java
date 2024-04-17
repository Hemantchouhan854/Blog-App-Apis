package com.app.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.app.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto  {

		private int id;
		@NotEmpty
		@Size(min=3,max = 25 ,message = "User name can't be small then 3 and greater than 25")
		private String name;
		@Email(message = "Not a valid e-mail address")
		private String email;
		@NotEmpty
		//We can use the @Pattern for the better e-mail check
//		@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
		private String password;
		@NotEmpty
		private String about;
		
		private Set<RoleDto> roles = new HashSet<>();
}
