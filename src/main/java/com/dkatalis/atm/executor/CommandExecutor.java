package com.dkatalis.atm.executor;

import com.dkatalis.atm.activity.Activity;
import com.dkatalis.atm.activity.DepositActivity;
import com.dkatalis.atm.activity.LoginActivity;
import com.dkatalis.atm.activity.LogoutActivity;
import com.dkatalis.atm.activity.TransferActivity;
import com.dkatalis.atm.activity.WithdrawActivity;
import com.dkatalis.atm.exception.InvalidInputException;
import com.dkatalis.atm.service.Command;

import static com.dkatalis.atm.constants.DisplayMessageConstants.SPACE_CHARACTER;

public class CommandExecutor {
	
	private TransferActivity transferActivity;
	public CommandExecutor(TransferActivity transferActivity, LoginActivity loginActivity,
			LogoutActivity logoutActivity, DepositActivity depositActivity, WithdrawActivity withdrawActivity) {
		super();
		this.transferActivity = transferActivity;
		this.loginActivity = loginActivity;
		this.logoutActivity = logoutActivity;
		this.depositActivity = depositActivity;
		this.withdrawActivity = withdrawActivity;
	}

	private LoginActivity loginActivity;
	private LogoutActivity logoutActivity;
	private DepositActivity depositActivity;
	private WithdrawActivity withdrawActivity;
	
	public void execute (String expression) {
		    getActivity(expression).execute(expression);
	}

	private Activity getActivity(final String expression) {
		Activity activity;
		String[] tokens = expression.split(SPACE_CHARACTER);
		Command command = Command.valueOf(tokens[0].toUpperCase());
		switch(command) {
		case TRANSFER:
		case TRANSFERRED:
			activity = transferActivity;
			break;
		case LOGIN: 
			activity = loginActivity;
			break;
		case LOGOUT:
			activity = logoutActivity;
			break;
		case DEPOSIT:
			activity = depositActivity;
			break;
		case WITHDRAW:
			activity = withdrawActivity;
			break;
		default:
			throw new InvalidInputException("Command not found, please enter a valid command");
		}
		return activity;
	}

}
