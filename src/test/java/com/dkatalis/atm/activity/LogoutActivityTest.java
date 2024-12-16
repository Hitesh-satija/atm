package com.dkatalis.atm.activity;

import com.dkatalis.atm.activity.dto.ActivityInput;
import com.dkatalis.atm.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LogoutActivityTest {

    @InjectMocks
    private LogoutActivity logoutActivity;

    @Mock
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcess_LogoutSuccess() {
        // Arrange
        String mockCustomerName = "JohnDoe";
        when(loginService.getCustomerName()).thenReturn(mockCustomerName);
        doNothing().when(loginService).logout();

        // Act
        logoutActivity.process(null); // Input is not used in process method.

        // Assert
        verify(loginService, times(1)).getCustomerName();
        verify(loginService, times(1)).logout();
        // Since the message is printed to the console, use a library like SystemOutRule for verification if needed.
    }


    @Test
    void testValidateInput_AlwaysReturnsTrue() {
        // Act
        boolean isValid = logoutActivity.validateInput(null);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testMapInputToActivityInputDto_AlwaysReturnsNull() {
        // Act
        ActivityInput activityInput = logoutActivity.mapInputToAcvityInputDto("LOGOUT");

        // Assert
        assertNull(activityInput);
    }
}
