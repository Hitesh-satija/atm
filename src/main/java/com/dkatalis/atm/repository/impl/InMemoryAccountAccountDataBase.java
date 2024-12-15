package com.dkatalis.atm.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.repository.AccountRepository;

/**
 * This is In memory DB containing Accounts
 */
public class InMemoryAccountAccountDataBase implements AccountRepository {
	
	private Map<String, Account> db;
	
	public InMemoryAccountAccountDataBase() {
		db = new HashMap<>();
	}
	
	@Override
	public Account get(final String name) {
		return db.get(name);
	}
	
	@Override
	public Account create(final Account account) {
		db.put(account.getName(), account);
		return get(account.getName());
	}

	@Override
	public Account update(Account account) {
		db.put(account.getName(), account);
		return get(account.getName());
	}

}
