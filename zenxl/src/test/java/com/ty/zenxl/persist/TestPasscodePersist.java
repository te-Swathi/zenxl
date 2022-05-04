package com.ty.zenxl.persist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ty.zenxl.entity.Passcode;
import com.ty.zenxl.repository.PasscodeRepository;

@DataJpaTest
class TestPasscodePersist {

	@Autowired
	private PasscodeRepository passcodeRepository;

	Passcode passcode1 = Passcode.builder().id(1).email("test1@gmail.com").expirationTime(System.currentTimeMillis())
			.passcode(111).build();

	@Test
	void returnPersistedPasscodeTest() {

		Passcode passcode = passcodeRepository.save(passcode1);

		assertThat(passcode.getEmail()).isEqualTo("test1@gmail.com");
		assertTrue(passcodeRepository.existsById(passcode.getId()));
	}

	@Test
	void persistPasscode() {

		passcodeRepository.save(passcode1);
		assertThat(passcodeRepository.findByEmail("test1@gmail.com")).isNotEmpty();

	}

	@Test
	void updatePasscode() {

		Passcode savedPasscode = passcodeRepository.save(passcode1);
		int initialPasscode = savedPasscode.getPasscode();

		savedPasscode.setPasscode(123);
		Passcode updatedPasscode = passcodeRepository.save(savedPasscode);
		int updatedPasscodeNo = updatedPasscode.getPasscode();
		assertFalse(initialPasscode == updatedPasscodeNo);
	}

}
