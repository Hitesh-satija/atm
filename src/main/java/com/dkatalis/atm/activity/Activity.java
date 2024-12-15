package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.exception.InvalidInputException;
import com.dkatalis.atm.exception.NotLogedInException;
import com.dkatalis.atm.service.LoginService;

public interface Activity {

	void process(final ActivityInput input);
	
	boolean validateInput(final ActivityInput input);
	
	ActivityInput mapInputToAcvityInputDto(final String input);
	
	
	default void execute(final String input) {
		ActivityInput inputActivity;
		try {
			inputActivity = mapInputToAcvityInputDto(input);
		} catch (Exception e) {
			throw new InvalidInputException("Please follow correct input formats!!!"+ e.getMessage());
		}
		if(validate(inputActivity)) {
			process(inputActivity);
		}else {
			throw new NotLogedInException("Please login first!");
		}
		
	}
	
	default boolean validate(final ActivityInput input) {
		if(this.getClass()==LoginActivity.class) {
			return true;
		}
		return LoginService.isCustomerLoggedIn() && validateInput(input);
	}

	default double parseDoubleValue(String input) {
		double amount;
		try {
			if(input.startsWith("$")) {
				input = input.substring(1);
			}
			amount = Double.parseDouble(input);
		}catch (NumberFormatException e) {
			throw new InvalidInputException("Please give correct value to withdraw");
		}
		return amount;
	}
}
