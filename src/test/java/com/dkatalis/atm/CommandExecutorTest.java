package com.dkatalis.atm;

import com.dkatalis.atm.activity.*;
import com.dkatalis.atm.exception.InvalidInputException;
import com.dkatalis.atm.executor.CommandExecutor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CommandExecutorTest {

    private TransferActivity transferActivity;
    private LoginActivity loginActivity;
    private LogoutActivity logoutActivity;
    private DepositActivity depositActivity;
    private WithdrawActivity withdrawActivity;
    private CommandExecutor commandExecutor;

    @BeforeEach
    void setUp() {
        transferActivity = mock(TransferActivity.class);
        loginActivity = mock(LoginActivity.class);
        logoutActivity = mock(LogoutActivity.class);
        depositActivity = mock(DepositActivity.class);
        withdrawActivity = mock(WithdrawActivity.class);

        commandExecutor = new CommandExecutor(transferActivity, loginActivity, logoutActivity, depositActivity, withdrawActivity);
    }

    @Test
    void testExecuteLoginCommand() {
        String command = "LOGIN user1";
        commandExecutor.execute(command);
        verify(loginActivity, times(1)).execute(command);
    }

    @Test
    void testExecuteLogoutCommand() {
        String command = "LOGOUT";
        commandExecutor.execute(command);
        verify(logoutActivity, times(1)).execute(command);
    }

    @Test
    void testExecuteDepositCommand() {
        String command = "DEPOSIT 100";
        commandExecutor.execute(command);
        verify(depositActivity, times(1)).execute(command);
    }

    @Test
    void testExecuteWithdrawCommand() {
        String command = "WITHDRAW 50";
        commandExecutor.execute(command);
        verify(withdrawActivity, times(1)).execute(command);
    }

    @Test
    void testExecuteTransferCommand() {
        String command = "TRANFER user2 200";
        commandExecutor.execute(command);
        verify(transferActivity, times(1)).execute(command);
    }

    @Test
    void testInvalidCommandThrowsException() {
        String invalidCommand = "INVALID COMMAND";
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            commandExecutor.execute(invalidCommand);
        });

        assertEquals("Command not found, please enter a valid command", exception.getMessage());
    }
}
