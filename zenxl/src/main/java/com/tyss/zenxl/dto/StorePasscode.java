package com.tyss.zenxl.dto;

public class StorePasscode {
	
	private StorePasscode() {
		super();
	}
	
	private static int passcode;

	public static int getPasscode() {
		return passcode;
	}

	public static void setPasscode(int passcode) {
		StorePasscode.passcode = passcode;
	}

	

}
