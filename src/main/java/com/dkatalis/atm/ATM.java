package com.dkatalis.atm;

import java.util.Scanner;

import com.dkatalis.atm.activity.DepositActivity;
import com.dkatalis.atm.activity.LoginActivity;
import com.dkatalis.atm.activity.LogoutActivity;
import com.dkatalis.atm.activity.TransferActivity;
import com.dkatalis.atm.activity.WithdrawActivity;
import com.dkatalis.atm.exception.InvalidInputException;
import com.dkatalis.atm.executor.CommandExecutor;
import com.dkatalis.atm.repository.AccountRepository;
import com.dkatalis.atm.repository.impl.InMemoryAccountAccountDataBase;
import com.dkatalis.atm.service.AccountService;
import com.dkatalis.atm.service.LoginService;
import com.dkatalis.atm.service.TransactionService;

public class ATM {
	
	private TransferActivity transferActivity;
	private LoginActivity loginActivity;
	private LogoutActivity logoutActivity;
	private DepositActivity depositActivity;
	private WithdrawActivity withdrawActivity;
	
	private TransactionService transactionService;
	private LoginService loginService; 
	private AccountService accountService;
	private CommandExecutor commandExecutor;
	
	private AccountRepository accountRepository;
	
	/**
	 * This class is called by main().
	 * This class initializes the context and start the ATM Flows. 
	 */
	public ATM() {
		this.accountRepository = new InMemoryAccountAccountDataBase();
		
		this.transactionService = new TransactionService(accountRepository);
		this.loginService = new LoginService();
		this.accountService = new AccountService(accountRepository);
		
		this.depositActivity = new DepositActivity(transactionService, loginService);
		this.logoutActivity = new LogoutActivity(loginService);
		this.withdrawActivity = new WithdrawActivity(transactionService, loginService);
		this.loginActivity = new LoginActivity(loginService, accountService);
		this.transferActivity = new TransferActivity(transactionService, loginService);
		this.commandExecutor = new CommandExecutor(transferActivity,
				loginActivity, logoutActivity, depositActivity, withdrawActivity);	
	}
	
	public void start() {
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("ATM started");
			while(true) {
				String input = scanner.nextLine().trim();
				try {
					commandExecutor.execute(input);
				} catch (InvalidInputException ex) {
					System.out.println("Please put in values in correct format. \n "
							+ "Please enter a correct recipient");
				}	
			}
		} catch (Exception ex) {
			// TODO send Error to Bank.
			ex.printStackTrace();
			System.out.println("This ATM is out of order! Please find another ATM nearby. Thanks\n"
			+ ex.getMessage());
			 try {
				Thread.sleep((long) 150000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
