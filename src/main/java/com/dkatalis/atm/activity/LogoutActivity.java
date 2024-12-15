package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.service.LoginService;

public class LogoutActivity implements Activity {
	
	final LoginService loginService;
	
	public LogoutActivity(final LoginService loginService) {
		this.loginService = loginService;
	}

	@Override
	public ActivityInput mapInputToAcvityInputDto(String input) {
		// Not Required
		return null;
	}

	@Override
	public void process(ActivityInput input) {
		String name = loginService.getCustomerName();
		this.loginService.logout();
		displayMessage(name);
	}

	private void displayMessage(final String name) {
		System.out.println("GoodBye, " + name + " !");
		
	}

	@Override
	public boolean validateInput(ActivityInput input) {
		// only command is required
		return true;
	}
}
