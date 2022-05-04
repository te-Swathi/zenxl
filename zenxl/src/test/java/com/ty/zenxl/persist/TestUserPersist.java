package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ty.zenxl.entity.User;
import com.ty.zenxl.repository.UserRepository;

@DataJpaTest
class TestUserPersist {

	@Autowired
	private UserRepository userRepository;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	User user1 = User.builder().username("test1").email("test1@gmail.com").dateOfBirth(new Date()).gender("MALE")
			.password(encoder.encode("test1")).build();

	@Test
	void fetchUserTest() {

		userRepository.save(user1);
		assertThat(userRepository.findByUsername("test1").get()).isNotNull();
		assertThat(userRepository.findByEmail("test1@gmail.com").get()).isNotNull();
	}
	
	@Test
	void persistUserTest() {

		userRepository.save(user1);
		assertTrue(userRepository.existsByUsername("test1"));
	}

	@Test
	void updateUserTest() {

		User savedUser = userRepository.save(user1);
		String intialEmail = savedUser.getEmail();
		
		User fetchedUser = userRepository.findById(savedUser.getUserId()).get();
		fetchedUser.setEmail("test123@gmail.com");
		
		User updatedUser = userRepository.save(fetchedUser);
		String updatedEmail = updatedUser.getEmail();
		
		assertThat(intialEmail).isNotEqualTo(updatedEmail);
	}

	@Test
	void deleteUserTest() {

		User savedUser = userRepository.save(user1);
		userRepository.deleteById(savedUser.getUserId());
		assertFalse(userRepository.existsByUsername(savedUser.getUsername()));
	}
}
