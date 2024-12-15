package com.dkatalis.atm.service;

import com.dkatalis.atm.exception.InsufficientBalanceException;
import com.dkatalis.atm.exception.InvalidInputException;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.repository.AccountRepository;

public class TransactionService { 
	
	private AccountRepository accountRepository;
	
	public TransactionService(final AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public Account withdraw(final String name, final double amount) {
		final Account account = accountRepository.get(name);
		if(amount>account.getBalance()) {
			throw new InsufficientBalanceException(
					"Insufficient balance!!. please enter a lesser amount");
		}
		account.decreaseBalance(amount);	
		return accountRepository.update(account);
	}
	
	public Account deposit(final String name, final double amount) {
		final Account account = accountRepository.get(name);
		double debt = account.getTotalDebt();
		double leftOverAmount = amount - debt;
		account.payDebt(amount);
		if(leftOverAmount > 0) {
			account.increaseBalance(leftOverAmount);
		} 
		return accountRepository.update(account);
	}
	
	public Account transfer(final String recipientName,
			final String senderName, double amount) {
		final Account senderAccount = accountRepository.get(senderName);
		final Account recipientAccount = accountRepository.get(recipientName);
		
		if(null == recipientAccount) {
			new InvalidInputException("Recipeint not present!!!");
		}

		double senderAccountBalance = senderAccount.getBalance();
		if(amount<senderAccountBalance) {
			transferAmount(senderAccount, recipientAccount, amount);
		}else {
			double differenceAmount = amount - senderAccountBalance;
			transferAmount(senderAccount, recipientAccount, senderAccountBalance);
			recordDebt(senderAccount, recipientAccount, differenceAmount);
		}
		return senderAccount;
		
	}
	
	public void recordDebt(final Account debtee, final Account debtor, double amount) {
		debtee.addPayableDebt(debtor.getName(), amount);
		debtor.addReceivableDebt(debtee.getName(), amount);	
	}
	
	private void transferAmount(final Account sender, final Account recipient, final double amount) {
		sender.decreaseBalance(amount);
		recipient.increaseBalance(amount);
		System.out.println("Transferred $" + amount + " to "+ recipient.getName());
	}
}
