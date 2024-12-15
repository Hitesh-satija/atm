package com.dkatalis.atm.exception;

public class AccountNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3233775090539602355L;

	public AccountNotFoundException(String message) {
		super(message);
	}
	
}
