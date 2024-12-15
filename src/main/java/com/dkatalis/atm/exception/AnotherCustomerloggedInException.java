package com.dkatalis.atm.exception;

public class AnotherCustomerloggedInException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7262150677615169100L;
	
	public AnotherCustomerloggedInException(String message) {
		super(message);
	}

}
