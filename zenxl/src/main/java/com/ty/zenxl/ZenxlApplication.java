package com.ty.zenxl;

import java.util.Date;
import java.util.Properties;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ty.zenxl.entity.Role;
import com.ty.zenxl.entity.User;
import com.ty.zenxl.repository.RoleRepository;
import com.ty.zenxl.repository.UserRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "ZeNXL"))
public class ZenxlApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("www.indrajit55@gmail.com");
		mailSender.setPassword("rsygyoxhxdqwxpyz");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	public static void main(String[] args) {
		SpringApplication.run(ZenxlApplication.class, args);
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public void run(String... args) throws Exception {

		User user = User.builder().username("indrajit").email("iamindrajitrana@yahoo.com")
				.dateOfBirth(new Date("1997/10/24")).gender("MALE").role(Role.builder().roleName("ROLE_ADMIN").build())
				.password(encoder().encode("indrajit123")).build();
		if(Boolean.FALSE.equals(roleRepository.existsByRoleName(user.getRole().getRoleName()))) {
			userRepository.save(user);
		}

	}
}
