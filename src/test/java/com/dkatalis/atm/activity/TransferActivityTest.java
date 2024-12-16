package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.exception.InvalidInputException;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.service.LoginService;
import com.dkatalis.atm.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransferActivityTest {

    @InjectMocks
    private TransferActivity transferActivity;

    @Mock
    private TransactionService transactionService;

    @Mock
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcess_SuccessfulTransfer() {
        // Arrange
        String sender = "Alice";
        String recipient = "Bob";
        double amount = 50.0;

        Map<String, Double> debtPayable = new HashMap<>();
        debtPayable.put("Charlie", 20.0);

        Map<String, Double> debtReceivable = new HashMap<>();
        debtReceivable.put("David", 30.0);

        Account mockAccount = Account.builder().withBalance(100.0).withName(sender).build();
        mockAccount.setDebtPayable(debtPayable);
        mockAccount.setDebtReceivable(debtReceivable);

        when(transactionService.transfer(recipient, sender, amount)).thenReturn(mockAccount);

        ActivityInput input = ActivityInput.Builder()
                .withName(sender)
                .withRecipientName(recipient)
                .withAmount(amount)
                .build();

        // Act
        transferActivity.process(input);

        // Assert
        verify(transactionService, times(1)).transfer(recipient, sender, amount);
        // Console output validation can be added using SystemOutRule if necessary.
    }

    @Test
    void testValidateInput_ValidInput() {
        // Arrange
        ActivityInput input = ActivityInput.Builder()
                .withName("Alice")
                .withRecipientName("Bob")
                .withAmount(50.0)
                .build();

        // Act & Assert
        assertTrue(transferActivity.validateInput(input));
    }

    @Test
    void testValidateInput_InvalidInput() {
        // Arrange
        ActivityInput input = ActivityInput.Builder()
                .withName(null) // Invalid name
                .withRecipientName("Bob")
                .withAmount(50.0)
                .build();

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> transferActivity.validateInput(input));
    }

    @Test
    void testMapInputToAcvityInputDto_ValidInput() {
        // Arrange
        String input = "TRANSFER 50 TO Bob";
        when(loginService.getCustomerName()).thenReturn("Alice");

        // Act
        ActivityInput activityInput = transferActivity.mapInputToAcvityInputDto(input);

        // Assert
        assertNotNull(activityInput);
        assertEquals("Alice", activityInput.getName());
        assertEquals("Bob", activityInput.getRecipientName());
        assertEquals(50.0, activityInput.getAmount());
    }

    @Test
    void testMapInputToAcvityInputDto_InvalidInputFormat() {
        // Arrange
        String input = "INVALID INPUT";

        // Act & Assert
        assertThrows(NumberFormatException.class, () -> transferActivity.mapInputToAcvityInputDto(input));
    }
}
