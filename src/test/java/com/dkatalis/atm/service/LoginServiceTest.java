package com.dkatalis.atm.service;

import com.dkatalis.atm.exception.AnotherCustomerloggedInException;
import com.dkatalis.atm.exception.NotLogedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private LoginService loginService;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
    }

    @Test
    void testLoginSuccess() {
        boolean result = loginService.login("customer1");
        assertTrue(result, "Login should succeed when no customer is logged in");
        assertEquals("customer1", loginService.getCustomerName(), "Customer name should be set after login");
    }

    @Test
    void testLoginFailsWhenAnotherCustomerIsLoggedIn() {
        loginService.login("customer1");
        AnotherCustomerloggedInException exception = assertThrows(AnotherCustomerloggedInException.class, () -> {
            loginService.login("customer2");
        });
        assertEquals("Please loggout before logging in.", exception.getMessage());
    }

    @Test
    void testLogoutSuccess() {
        loginService.login("customer1");
        boolean result = loginService.logout();
        assertTrue(result, "Logout should succeed when a customer is logged in");
        assertFalse(LoginService.isCustomerLoggedIn(), "No customer should be logged in after logout");
    }

    @Test
    void testLogoutFailsWhenNoCustomerIsLoggedIn() {
        NotLogedInException exception = assertThrows(NotLogedInException.class, () -> {
            loginService.logout();
        });
        assertEquals("No CustomerIsLoggedIn", exception.getMessage());
    }

    @Test
    void testGetCustomerNameWhenLoggedIn() {
        loginService.login("customer1");
        assertEquals("customer1", loginService.getCustomerName(), "Should return the logged-in customer name");
    }

    @Test
    void testGetCustomerNameThrowsExceptionWhenNotLoggedIn() {
        NotLogedInException exception = assertThrows(NotLogedInException.class, () -> {
            loginService.getCustomerName();
        });
        assertEquals("No customer Logged In", exception.getMessage());
    }

    @Test
    void testIsCustomerLoggedIn() {
        assertFalse(LoginService.isCustomerLoggedIn(), "Initially, no customer should be logged in");
        loginService.login("customer1");
        assertTrue(LoginService.isCustomerLoggedIn(), "Should return true when a customer is logged in");
    }
}
