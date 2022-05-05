
package com.ty.zenxl.service;

import static com.ty.zenxl.pojos.ZenxlConstantData.*;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ty.zenxl.entity.Passcode;
import com.ty.zenxl.entity.Role;
import com.ty.zenxl.entity.User;
import com.ty.zenxl.exception.ChangePasswordException;
import com.ty.zenxl.exception.EmailInterruptionException;
import com.ty.zenxl.exception.LoginException;
import com.ty.zenxl.exception.SignUpException;
import com.ty.zenxl.exception.UserNotFoundException;
import com.ty.zenxl.pojos.ChangePassword;
import com.ty.zenxl.pojos.ChangePasswordResponse;
import com.ty.zenxl.pojos.ForgotPasswordResponse;
import com.ty.zenxl.pojos.JwtToken;
import com.ty.zenxl.pojos.JwtUtils;
import com.ty.zenxl.pojos.LoginRequest;
import com.ty.zenxl.pojos.LoginResponse;
import com.ty.zenxl.pojos.SignUpRequest;
import com.ty.zenxl.pojos.SignUpResponse;
import com.ty.zenxl.pojos.SignnedUpUser;
import com.ty.zenxl.repository.PasscodeRepository;
import com.ty.zenxl.repository.RoleRepository;
import com.ty.zenxl.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ZenxlAuthService {

	private final CustomUserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasscodeRepository passcodeRepository;
	private final JwtUtils jwtUtils;
	private final JavaMailSender emailSender;
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	public LoginResponse authenticateUser(LoginRequest request) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (AuthenticationException e) {
			throw new LoginException(INCORRECT_USERNAME_AND_PASSWORD);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

		final String generateJwtToken = jwtUtils.generateToken(userDetails);
		return new LoginResponse(IS_ERROR_FALSE, LOGIN_SUCCESSFUL, new JwtToken(generateJwtToken));
	}

	public SignUpResponse addUser(SignUpRequest request) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(request.getUsername()))) {
			throw new SignUpException(USERNAME_ALREADY_EXISTS);
		}
		if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
			throw new SignUpException(EMAIL_ALREADY_EXISTS);
		}

		Role role = roleRepository.findByRoleName(request.getRole())
				.orElse(Role.builder().roleName(request.getRole()).build());

		User user = User.builder().username(request.getUsername()).email(request.getEmail())
				.dateOfBirth(request.getDateOfBirth()).gender(request.getGender())
				.password(encoder.encode(request.getPassword())).role(role).build();

		User savedUser = userRepository.save(user);

		if (savedUser.getUsername() != null) {

			return new SignUpResponse(IS_ERROR_FALSE, SIGN_UP_SUCCESSFUL,
					new SignnedUpUser(savedUser.getUserId(), savedUser.getUsername()));
		}
		throw new SignUpException(SIGN_UP_UNSUCCESSFUL);
	}

	public ForgotPasswordResponse forgotPassword(String email) {

		Optional<User> findByEmail = userRepository.findByEmail(email);
		if (Boolean.FALSE.equals(findByEmail.isPresent())) {
			throw new UserNotFoundException(USER_NOT_FOUND_WITH_THIS_EMAIL_ADDRESS);
		}

		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setSubject("Passcode For Password Reset.");
			int passcode = new Random().nextInt(100000, 999999);

			Passcode builtPasscode = Passcode.builder().passcode(passcode).email(email)
					.expirationTime(System.currentTimeMillis() + (1 * 60 * 1000)).build();

			Optional<Passcode> findByEmailInPasscode = passcodeRepository.findByEmail(email);
			if (findByEmailInPasscode.isPresent()) {
				Passcode initialPasscode = findByEmailInPasscode.get();
				initialPasscode.setPasscode(passcode);
				initialPasscode.setExpirationTime(System.currentTimeMillis() + (1 * 60 * 1000));
				passcodeRepository.save(initialPasscode);
			} else {
				passcodeRepository.save(builtPasscode);
			}

			mimeMessageHelper.setText("\n" + "Hello " + findByEmail.get().getUsername() + "," + "\n\n"
					+ "Please use this passcode to reset your password " + passcode + "\n\n" + "Thank you.");

			emailSender.send(mimeMessage);
		} catch (MailException | MessagingException e) {
			throw new EmailInterruptionException(e.getMessage());
		}

		return new ForgotPasswordResponse(IS_ERROR_FALSE, PASSCODE_HAS_BEEN_SENT);
	}

	public ChangePasswordResponse changePassword(ChangePassword request) {
		Optional<Passcode> passcode = passcodeRepository.findByEmail(request.getEmail());
		if (passcode.isPresent()) {

			if (request.getPasscode() == (passcode.get().getPasscode())
					&& passcode.get().getExpirationTime() > System.currentTimeMillis()) {

				if (!Objects.equals(request.getPassword(), request.getRePassword())) {
					throw new ChangePasswordException(BOTH_PASSWORDS_SHOULD_BE_SAME);
				}

				Optional<User> findByEmail = userRepository.findByEmail(request.getEmail());
				User user;
				if (findByEmail.isPresent()) {
					user = findByEmail.get();

					if (encoder.matches(request.getPassword(), user.getPassword())) {
						throw new ChangePasswordException(OLD_PASSWORD_AND_NEW_PASSWORD_SHOULD_BE_DIFFERENT);
					}

					user.setPassword(encoder.encode(request.getPassword()));
					userRepository.save(user);

					return new ChangePasswordResponse(IS_ERROR_FALSE, PASSWORD_RESET_SUCCESSFUL);
				}
				throw new ChangePasswordException(USER_DOESN_T_EXIST_WITH_THE_ENTERED_EMAIL);
			}
			throw new ChangePasswordException(ENTERED_PASSCODE_NOT_VALID);
		}
		throw new ChangePasswordException(PASSCODE_NOT_FOUND_WITH_ENTERED_EMAIL);
	}
}
