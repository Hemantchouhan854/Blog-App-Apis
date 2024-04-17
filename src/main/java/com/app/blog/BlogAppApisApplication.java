package com.app.blog;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.blog.config.AppContants;
import com.app.blog.entities.Role;
import com.app.blog.repositories.RoleRepo;

import jakarta.validation.constraints.AssertFalse.List;

@SpringBootApplication
public class BlogAppApisApplication  implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			Role role=new Role();
			role.setId(AppContants.NORMAL_USER);
			role.setName("ROLE_NORMAL");
			
			Role role1=new Role();
			role1.setId(AppContants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			java.util.List<Role> roles = java.util.List.of(role,role1);
			java.util.List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
