package com.dkatalis.atm.repository;

import com.dkatalis.atm.model.Account;

public interface AccountRepository {

	Account get(String name);

	Account create(Account account);
	
	Account update(Account account);

}