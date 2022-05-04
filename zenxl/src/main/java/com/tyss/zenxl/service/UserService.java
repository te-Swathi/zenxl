package com.tyss.zenxl.service;

import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.tyss.zenxl.dto.ForgotPassword;
import com.tyss.zenxl.dto.StorePasscode;
import com.tyss.zenxl.entity.User;
import com.tyss.zenxl.exception.OldPasswordException;
import com.tyss.zenxl.exception.ProperEmailException;
import com.tyss.zenxl.exception.UserAuthenticationFailedException;
import com.tyss.zenxl.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	public User addUser(User user) {
		User info = null;
		String str = "";
		try {
			if (str.equals(user.getUserName()) || str.equals(user.getPassword()) || str.equals(user.getEmail())) {
				throw new Exception("THIS FIELDS CANNOT BE EMPTY");

			} else {

				info = userRepository.save(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	public User userAuthentication(User user) throws UserAuthenticationFailedException {
		User user1 = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

		if (user1 != null && user.getEmail().equals(user1.getEmail())
				&& user.getPassword().equals(user1.getPassword())) {
			return user1;
		}

		else {
			throw new UserAuthenticationFailedException(
					"User authentication failed, Please enter valid email and password");

		}
	}

	public User getUserById(int id) {

		return userRepository.findById(id).orElse(null);
	}

	public User updateUser(User user) {

		User userUpdate = userRepository.findByEmail(user.getEmail());

		userUpdate.setUserName(user.getUserName());
		userUpdate.setEmail(user.getEmail());
		userUpdate.setDob(user.getDob());
		userUpdate.setGender(user.getGender());

		return userUpdate;

	}

	public User forgotPassword(ForgotPassword password) throws OldPasswordException, ProperEmailException {

		User userPassword = userRepository.findByEmail(password.getEmail());

		if (userPassword != null) {

			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setTo(userPassword.getEmail());
			mailMessage.setSubject("Greetings");
			mailMessage.setFrom("swathi.gowdacse@gmail.com");
			mailMessage.setText("<h1>Your request for the passcode generation is successful and the passcode is </h1>"
					+ otpGenerate());

			emailSenderService.sendEmail(mailMessage);

			if (password.getPasscode() != 0 && !(userPassword.getPassword().equals(password.getPassword()))) {

				userPassword.setPassword(password.getPassword());
				userRepository.save(userPassword);
			}

			else {
				throw new OldPasswordException("The entered password in old one. Please enter new password");
			}
		}

		else {
			throw new ProperEmailException("Please enter proper mail id");
		}

		return userPassword;
	}

	public static String otpGenerate() {
		SecureRandom rand = new SecureRandom();
		int random = rand.nextInt(10000);
		StorePasscode.setPasscode(random);
		return "otp is" + random;

	}// end of otpGenerate()

}
