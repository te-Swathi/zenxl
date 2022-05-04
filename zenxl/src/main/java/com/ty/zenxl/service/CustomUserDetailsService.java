package com.ty.zenxl.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ty.zenxl.entity.User;
import com.ty.zenxl.pojos.CustomUserDetails;
import com.ty.zenxl.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User findByUsername = userRepository.findByUsername(username)
								.orElseThrow(() -> new UsernameNotFoundException("User not found with the name "+username));
		
		return new CustomUserDetails(findByUsername);
	}

}
