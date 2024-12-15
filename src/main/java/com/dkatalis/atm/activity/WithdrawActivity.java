package com.dkatalis.atm.activity;


import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.exception.NotLogedInException;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.service.LoginService;
import com.dkatalis.atm.service.TransactionService;

import static com.dkatalis.atm.constants.DisplayMessageConstants.NEW_LINE_CHARACTER;
import static com.dkatalis.atm.constants.DisplayMessageConstants.SPACE_CHARACTER;;


public class WithdrawActivity implements Activity {

	private TransactionService transactionService;
	private LoginService loginService;
	
	public WithdrawActivity(final TransactionService transactionService,
			final LoginService loginService) {
		this.transactionService = transactionService;
		this.loginService = loginService;
	}
	
	@Override
	public void process(ActivityInput input) {
		final Account account = transactionService.withdraw(input.getName(), input.getAmount());
		displayMessage(account, input.getAmount());
	}


	@Override
	public boolean validateInput(ActivityInput input) {
		if(null == input.getName()) {
			throw new NotLogedInException("No login found!!");
		}
		return true;
	}

	@Override
	public ActivityInput mapInputToAcvityInputDto(String input) {
		String[] tokens = input.split(SPACE_CHARACTER);
		double amount = parseDoubleValue(tokens[1]);
		String name = loginService.getCustomerName();
		return ActivityInput.Builder()
				.withAmount(amount)
				.withName(name).build();
	}
	
	
	private void displayMessage(final Account account, double amount) {
		String message =  new StringBuffer()
			.append("Withdrawn $")
			.append(amount)
			.append(NEW_LINE_CHARACTER)
			.append("Your balance is $")
			.append(account.getBalance()).toString();
		System.out.println(message);
	}

}
