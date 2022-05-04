package com.tyss.zenxl.repository;

import org.springframework.data.repository.CrudRepository;

import com.tyss.zenxl.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
	public User findByEmailAndPassword(String email, String password);
	
	public User findByEmail(String email);

}
