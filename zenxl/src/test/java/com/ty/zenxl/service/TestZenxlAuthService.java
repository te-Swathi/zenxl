package com.ty.zenxl.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.ty.zenxl.entity.User;
import com.ty.zenxl.pojos.CustomUserDetails;
import com.ty.zenxl.pojos.JwtUtils;
import com.ty.zenxl.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class TestZenxlAuthService {
	
	@Mock
	private CustomUserDetailsService userDetailsService;

	@Mock
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtUtils jwtUtils;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	User user = User.builder().username("test").email("test@gmail.com").dateOfBirth(new Date()).gender("MALE")
			.password(encoder.encode("test")).build();

	CustomUserDetails customUserDetails = new CustomUserDetails(user);

	String jsonContent = "{\"username\": \"test\",\"password\": \"test\"}";
	
	/*
	 * Test Case for signing up of an user.
	 */
	@Test
	void testSignUp() {

		when(userRepository.save(any(User.class))).thenReturn(user);

		User savedUser = userRepository.save(user);

		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("test");
	}

	/*
	 * Test Case for login in of a valid user.
	 */

	@Test
	void testLogin() throws Exception {
		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(customUserDetails);

		UserDetails userDetails = userDetailsService.loadUserByUsername("test");
		
		assertThat(userDetails.getUsername()).isEqualTo("test");


		Authentication authenticate = 
				new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		assertThat(authenticate.getName()).isEqualTo("test");
	}

}
