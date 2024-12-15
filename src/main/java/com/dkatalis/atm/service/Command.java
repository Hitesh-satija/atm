package com.dkatalis.atm.service;

	
public enum Command {
		TRANSFERRED 	("transferred"),
		TRANSFER 	("transfer"),	// Transfers this amount from the logged in customer to the target customer
		LOGIN		("login"),	// Logs in as this customer and creates the customer if not exist
		WITHDRAW	("withdraw"),	// Withdraws this amount from the logged in customer
		DEPOSIT 	("deposit"),    // Deposits this amount to the logged in customer
		LOGOUT		("logout")// Logs out of the current customer	
;

		public final String value;
		
		
		Command(String string) {
			this.value = string;
		}
}
