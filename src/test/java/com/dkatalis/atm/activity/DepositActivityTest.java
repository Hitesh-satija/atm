package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.service.LoginService;
import com.dkatalis.atm.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepositActivityTest {

    private TransactionService transactionService;
    private LoginService loginService;
    private DepositActivity depositActivity;

    @BeforeEach
    void setUp() {
        transactionService = mock(TransactionService.class);
        loginService = mock(LoginService.class);
        depositActivity = new DepositActivity(transactionService, loginService);
    }

    @Test
    void testProcess() {
        String customerName = "customer1";
        double depositAmount = 100.0;
        Account account = Account.builder()
        		.withName(customerName)
        		.withBalance(depositAmount)
        		.build();

        when(transactionService.deposit(customerName, depositAmount)).thenReturn(account);

        ActivityInput input = ActivityInput.Builder()
                .withAmount(depositAmount)
                .withName(customerName)
                .build();

        depositActivity.process(input);

        verify(transactionService, times(1)).deposit(customerName, depositAmount);
    }

    @Test
    void testMapInputToActivityInputDto() {
        String rawInput = "DEPOSIT 100";
        String customerName = "customer1";
        double depositAmount = 100.0;

        when(loginService.getCustomerName()).thenReturn(customerName);

        ActivityInput input = depositActivity.mapInputToAcvityInputDto(rawInput);

        assertEquals(customerName, input.getName(), "Customer name should match");
        assertEquals(depositAmount, input.getAmount(), "Deposit amount should match");
    }

    @Test
    void testDisplayMessage() {
    	
    	String customerName = "customer1";
        double depositAmount = 300.0;
        Account account = Account.builder()
        		.withName(customerName)
        		.withBalance(depositAmount)
        		.build();

        // Capture system output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        depositActivity.process(ActivityInput.Builder()
                .withAmount(100.0)
                .withName("customer1")
                .build());

        String expectedMessage = "Your Balance is $300.0\n";
        assertTrue(outContent.toString().contains(expectedMessage), "Expected output message to match");

        System.setOut(System.out); // Reset System.out
    }

    @Test
    void testValidateInput() {
        ActivityInput input = ActivityInput.Builder()
                .withAmount(100.0)
                .withName("customer1")
                .build();

        assertTrue(depositActivity.validateInput(input), "Input validation should return true");
    }
}
