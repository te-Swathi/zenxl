package com.ty.zenxl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ty.zenxl.entity.User;
import com.ty.zenxl.pojos.CustomUserDetails;

@ExtendWith(MockitoExtension.class)
class TestCustomUserDetailsService {
	
	@Mock
	private CustomUserDetailsService userDetailsService;
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	User user = User.builder().username("test").email("test@gmail.com").dateOfBirth(new Date()).gender("MALE")
			.password(encoder.encode("test")).build();

	@Test
	void testLoadUserByUsername() {
		
		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new CustomUserDetails(user));
		
		UserDetails loadUserByUsername = userDetailsService.loadUserByUsername("test");
		
		assertEquals(user.getUsername(), loadUserByUsername.getUsername());
		assertEquals(user.getPassword(), loadUserByUsername.getPassword());
	}

}
