package com.dkatalis.atm.exception;

public class NotLogedInException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3581846897388078746L;

	public NotLogedInException(String message) {
		super(message);
	}

}
