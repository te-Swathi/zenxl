package com.ty.zenxl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ty.zenxl.entity.Role;
import com.ty.zenxl.entity.User;
import com.ty.zenxl.repository.RoleRepository;
import com.ty.zenxl.repository.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ZeNXL"))
@RequiredArgsConstructor
public class ZenxlApplication implements CommandLineRunner{
	
	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public void run(String... args) throws Exception {

		User user = User.builder().username("indrajit").email("iamindrajitrana@yahoo.com")
				.dateOfBirth(new Date("1997/10/24")).gender("MALE").role(Role.builder().roleName("ROLE_ADMIN").build())
				.password(encoder.encode("indrajit123")).build();
		if (Boolean.FALSE.equals(roleRepository.existsByRoleName(user.getRole().getRoleName()))) {
			userRepository.save(user);
		}

	}
	

	public static void main(String[] args) {
		SpringApplication.run(ZenxlApplication.class, args);
	}

}
