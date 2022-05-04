package com.tyss.zenxl.dto;

public class ForgotPassword {
	
	private String email;
	
	private String password;
	
	private int passcode;
	
	

	public ForgotPassword() {
		super();
	}
	
	

	public ForgotPassword(String email) {
		super();
		this.email = email;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPasscode() {
		return passcode;
	}

	public void setPasscode(int passcode) {
		this.passcode = passcode;
	}

	

	
	

}
