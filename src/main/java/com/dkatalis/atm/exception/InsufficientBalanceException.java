package com.dkatalis.atm.exception;

public class InsufficientBalanceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -993739804602792870L;
	
	public InsufficientBalanceException(String message) {
		super(message);
	}

}
