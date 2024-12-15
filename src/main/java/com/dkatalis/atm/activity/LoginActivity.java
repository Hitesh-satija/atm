package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;


import com.dkatalis.atm.exception.AnotherCustomerloggedInException;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.service.AccountService;
import com.dkatalis.atm.service.LoginService;

import static com.dkatalis.atm.constants.DisplayMessageConstants.SPACE_CHARACTER;


public class LoginActivity implements Activity {
	
	private static final String NEXT_LINE_CHARACTER = "\n";
	
	private LoginService loginService;
	private AccountService accountService;
	
	public LoginActivity(final LoginService loginService,
			final AccountService accountService) {
		this.accountService = accountService;
		this.loginService = loginService;
	}
	
	private String getLoginMessage(final Account account) {
		StringBuffer messageBuffer = new StringBuffer("Hello, ")
				.append(account.getName())
				.append("!")
				.append(NEXT_LINE_CHARACTER)
				.append("Your balance is $")
				.append(account.getBalance());
		
		return messageBuffer.toString();
		
	}

	@Override
	public void process(final ActivityInput input) {
		try{
			Account account = accountService.getOrCreate(input.getName());
			loginService.login(input.getName());
			System.out.println(getLoginMessage(account));
		} catch (AnotherCustomerloggedInException e) {
			System.out.println("Another Customer already logged in, please logout first!");
		}	
	}

	@Override
	public ActivityInput mapInputToAcvityInputDto(final String input) {
		String[] tokens = input.split(SPACE_CHARACTER);
		String name = tokens[1];
		return ActivityInput.Builder().withName(name).build();
	}

	@Override
	public boolean validateInput(ActivityInput input) {
//		return null != input.getName() && !input.getName().isBlank();
		return true;
	}

}
