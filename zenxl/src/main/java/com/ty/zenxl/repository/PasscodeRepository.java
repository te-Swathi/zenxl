package com.ty.zenxl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.zenxl.entity.Passcode;

public interface PasscodeRepository extends JpaRepository<Passcode, Integer> {

	Optional<Passcode> findByEmail(String email);

}
