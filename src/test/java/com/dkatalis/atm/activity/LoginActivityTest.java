package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.exception.AnotherCustomerloggedInException;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.service.AccountService;
import com.dkatalis.atm.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginActivityTest {

    @InjectMocks
    private LoginActivity loginActivity;

    @Mock
    private LoginService loginService;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcess_SuccessfulLogin() {
        // Arrange
        String userName = "JohnDoe";
        Account mockAccount = Account.builder().withBalance(100.0).withName(userName).build();
        when(accountService.getOrCreate(userName)).thenReturn(mockAccount);
        doNothing().when(loginService).login(userName);

        ActivityInput input = ActivityInput.Builder().withName(userName).build();

        // Act
        loginActivity.process(input);

        // Assert
        verify(accountService, times(1)).getOrCreate(userName);
        verify(loginService, times(1)).login(userName);
        // Add assertion for printed output if needed using a library like SystemOutRule
    }

    @Test
    void testProcess_AnotherCustomerLoggedInException() {
        // Arrange
        String userName = "JohnDoe";
        Account account = Account.builder().withBalance(100.0).withName(userName).build();
        when(accountService.getOrCreate(userName)).thenReturn(account);
        doThrow(new AnotherCustomerloggedInException("Another Customer already logged in"))
                .when(loginService).login(userName);

        ActivityInput input = ActivityInput.Builder().withName(userName).build();

        // Act
        loginActivity.process(input);

        // Assert
        verify(accountService, times(1)).getOrCreate(userName);
        verify(loginService, times(1)).login(userName);
        // Add assertion for printed output if needed
    }

    @Test
    void testMapInputToActivityInputDto() {
        // Arrange
        String input = "LOGIN JohnDoe";

        // Act
        ActivityInput activityInput = loginActivity.mapInputToAcvityInputDto(input);

        // Assert
        assertNotNull(activityInput);
        assertEquals("JohnDoe", activityInput.getName());
    }

    @Test
    void testValidateInput_ValidInput() {
        // Arrange
        ActivityInput input = ActivityInput.Builder().withName("JohnDoe").build();

        // Act
        boolean isValid = loginActivity.validateInput(input);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testValidateInput_InvalidInput() {
        // Arrange
        ActivityInput input = ActivityInput.Builder().withName("").build();

        // Act
        boolean isValid = loginActivity.validateInput(input);

        // Assert
        assertFalse(isValid);
    }
}
