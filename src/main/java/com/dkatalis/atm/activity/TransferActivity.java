package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.exception.InvalidInputException;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.service.LoginService;
import com.dkatalis.atm.service.TransactionService;

import static com.dkatalis.atm.constants.DisplayMessageConstants.SPACE_CHARACTER;
import static com.dkatalis.atm.constants.DisplayMessageConstants.NEW_LINE_CHARACTER;

public class TransferActivity implements Activity {
	
	private TransactionService transactionService;
	private LoginService loginService;
	
	public TransferActivity(final TransactionService transactionService,
			final LoginService loginService) {
		this.transactionService = transactionService;
		this.loginService = loginService;
	}
	

	@Override
	public void process(ActivityInput input) {
		Account account = transactionService.transfer(input.getRecipientName(),
				input.getName(), input.getAmount());
		displayMessage(account);		
	}


	private void displayMessage(Account account) {
		StringBuffer messageStringBuffer = new StringBuffer(
				"Your Balance is $").append(account.getBalance());
		
		account.getDebtPayable().entrySet().forEach(debtEntry-> {
			messageStringBuffer
			.append("owed $")
			.append(debtEntry.getValue())
			.append(" to ")
			.append(debtEntry.getKey());
		});
		
		
		account.getDebtReceivable().entrySet().forEach(receivableEntry -> {
			messageStringBuffer
			.append(NEW_LINE_CHARACTER)
			.append("owed $")
			.append(receivableEntry.getValue())
			.append(" from ")
			.append(receivableEntry.getKey());
		});
		
		System.out.println(messageStringBuffer.toString());
	}


	@Override
	public boolean validateInput(ActivityInput input) {
		if(null == input.getName() || null == input.getRecipientName()) {
			throw new InvalidInputException("Please use correct input formats");
		}
		return true;
	}


	@Override
	public ActivityInput mapInputToAcvityInputDto(String input) {
		String[] tokens = input.split(SPACE_CHARACTER);
		String name = loginService.getCustomerName();
		String recipientName = tokens[3];
		Double amount = parseDoubleValue(tokens[1]);
		
		return ActivityInput.Builder()
				.withAmount(amount)
				.withName(name)
				.withRecipientName(recipientName).build();
	}

}
