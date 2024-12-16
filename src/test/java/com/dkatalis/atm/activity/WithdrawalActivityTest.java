package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.exception.NotLogedInException;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.service.LoginService;
import com.dkatalis.atm.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WithdrawActivityTest {

    @InjectMocks
    private WithdrawActivity withdrawActivity;

    @Mock
    private TransactionService transactionService;

    @Mock
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcess_SuccessfulWithdrawal() {
        // Arrange
        String customerName = "Alice";
        double withdrawAmount = 50.0;
        Account mockAccount = Account.builder().withBalance(100.0).withName(customerName).build();

        when(transactionService.withdraw(customerName, withdrawAmount)).thenReturn(mockAccount);

        ActivityInput input = ActivityInput.Builder()
                .withName(customerName)
                .withAmount(withdrawAmount)
                .build();

        // Act
        withdrawActivity.process(input);

        // Assert
        verify(transactionService, times(1)).withdraw(customerName, withdrawAmount);
        // You can add console output validation using SystemOutRule if needed.
    }

    @Test
    void testValidateInput_ValidInput() {
        // Arrange
        ActivityInput input = ActivityInput.Builder()
                .withName("Alice")
                .withAmount(50.0)
                .build();

        // Act & Assert
        assertTrue(withdrawActivity.validateInput(input));
    }

    @Test
    void testValidateInput_InvalidInput() {
        // Arrange
        ActivityInput input = ActivityInput.Builder()
                .withName(null) // Invalid input
                .withAmount(50.0)
                .build();

        // Act & Assert
        assertThrows(NotLogedInException.class, () -> withdrawActivity.validateInput(input));
    }

    @Test
    void testMapInputToAcvityInputDto_ValidInput() {
        // Arrange
        String input = "WITHDRAW 50";
        when(loginService.getCustomerName()).thenReturn("Alice");

        // Act
        ActivityInput activityInput = withdrawActivity.mapInputToAcvityInputDto(input);

        // Assert
        assertNotNull(activityInput);
        assertEquals("Alice", activityInput.getName());
        assertEquals(50.0, activityInput.getAmount());
    }

    @Test
    void testMapInputToAcvityInputDto_InvalidInputFormat() {
        // Arrange
        String input = "INVALID INPUT";

        // Act & Assert
        assertThrows(NumberFormatException.class, () -> withdrawActivity.mapInputToAcvityInputDto(input));
    }
}
