package com.dkatalis.atm.service;

import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void testGetOrCreateWhenAccountExists() {
        Account existingAccount = Account.builder().withName("customer1").build();
        when(accountRepository.get("customer1")).thenReturn(existingAccount);

        Account result = accountService.getOrCreate("customer1");

        assertEquals(existingAccount, result, "Should return the existing account when it already exists");
        verify(accountRepository, never()).create(any(Account.class));
    }

    @Test
    void testGetOrCreateWhenAccountDoesNotExist() {
        when(accountRepository.get("customer1")).thenReturn(null);
        Account newAccount = Account.builder().withName("customer1").build();
        when(accountRepository.create(any(Account.class))).thenReturn(newAccount);

        Account result = accountService.getOrCreate("customer1");

        assertEquals(newAccount, result, "Should create and return a new account when it does not exist");
        verify(accountRepository, times(1)).create(any(Account.class));
    }

    @Test
    void testCreate() {
        Account newAccount = Account.builder().withName("customer1").build();
        when(accountRepository.create(any(Account.class))).thenReturn(newAccount);

        Account result = accountService.create("customer1");

        assertEquals(newAccount, result, "Should create and return a new account");
        verify(accountRepository, times(1)).create(any(Account.class));
    }

    @Test
    void testGet() {
        Account existingAccount = Account.builder().withName("customer1").build();
        when(accountRepository.get("customer1")).thenReturn(existingAccount);

        Account result = accountService.get("customer1");

        assertEquals(existingAccount, result, "Should return the account from the repository");
        verify(accountRepository, times(1)).get("customer1");
    }

    @Test
    void testUpdate() {
        Account updatedAccount = Account.builder().withName("customer1").build();
        when(accountRepository.update(any(Account.class))).thenReturn(updatedAccount);

        Account result = accountService.update(updatedAccount);

        assertEquals(updatedAccount, result, "Should update and return the account");
        verify(accountRepository, times(1)).update(updatedAccount);
    }
}
