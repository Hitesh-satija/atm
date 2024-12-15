package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.service.LoginService;
import com.dkatalis.atm.service.TransactionService;

import static com.dkatalis.atm.constants.DisplayMessageConstants.SPACE_CHARACTER;

public class DepositActivity implements Activity {

	private TransactionService transactionService;
	private LoginService loginService;
	
	public DepositActivity(final TransactionService transactionService,
			final LoginService loginService) {
		this.transactionService = transactionService;
		this.loginService = loginService;
	}
	
	@Override
	public void process(ActivityInput input) {
		final Account account = transactionService.deposit(input.getName(), input.getAmount());
		displayMessage(account);
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


	private void displayMessage(Account account) {
		String message =  new StringBuffer("Your Balance is $")
				.append(account.getBalance()).toString();
		System.out.println(message);
	}

	@Override
	public boolean validateInput(ActivityInput input) {
		// TODO Auto-generated method stub
		return true;
	}

}
