package com.dkatalis.atm.service;


import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.repository.AccountRepository;

public class AccountService {
	
	private AccountRepository accountRepository;
	
	public AccountService(final AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public Account getOrCreate(final String name) {
		Account customerInDB = get(name);
		return null == customerInDB ? create(name): customerInDB;
	}
	
	public Account create(final String name) {
		final Account newAccount = Account.builder().withName(name).build();
		return accountRepository.create(newAccount);
	}

	public Account get(final String name) {
		return accountRepository.get(name);			
	}
	
	public Account update(final Account account) {
		return accountRepository.update(account);
	}

}
