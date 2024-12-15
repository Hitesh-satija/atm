package com.dkatalis.atm.service;

import java.util.Optional;

import com.dkatalis.atm.exception.AnotherCustomerloggedInException;
import com.dkatalis.atm.exception.NotLogedInException;

public class LoginService {
	
	private static String loggedInCustomerName;
	
	public boolean login(final String name) {
		if(!isCustomerLoggedIn()) {
			loggedInCustomerName = name;
			return true;
		}else {
			throw new AnotherCustomerloggedInException("Please loggout before logging in.");
		}
	}
	
	public boolean logout() {
		if(isCustomerLoggedIn()) {
			loggedInCustomerName = null;
			return true;
		}else {
			throw new NotLogedInException("No CustomerIsLoggedIn");
		}
	}
	
	public String getCustomerName() {
		return Optional.of(loggedInCustomerName)
				.orElseThrow(() -> new NotLogedInException("No customer Logged In"));
	}
	
	public static boolean isCustomerLoggedIn() {
		return null != loggedInCustomerName;
	}

}
