package com.dkatalis.atm.repository.impl;

import com.dkatalis.atm.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAccountAccountDatabaseTest {

    private InMemoryAccountAccountDataBase accountDatabase;

    @BeforeEach
    void setUp() {
        accountDatabase = new InMemoryAccountAccountDataBase();
    }

    @Test
    void testCreateAndGetAccount() {
        // Arrange
        Account account = Account.builder().withName("Alice").withBalance(100.0).build();

        // Act
        Account createdAccount = accountDatabase.create(account);
        Account fetchedAccount = accountDatabase.get("Alice");

        // Assert
        assertNotNull(createdAccount);
        assertEquals("Alice", createdAccount.getName());
        assertEquals(100.0, createdAccount.getBalance());
        assertEquals(createdAccount, fetchedAccount);
    }

    @Test
    void testGetNonExistingAccount() {
        // Act
        Account fetchedAccount = accountDatabase.get("NonExisting");

        // Assert
        assertNull(fetchedAccount, "Fetching a non-existing account should return null.");
    }

    @Test
    void testUpdateAccount() {
        // Arrange
        Account account = Account.builder().withName("Alice").withBalance(100.0).build();
        accountDatabase.create(account);

        // Act
        account.increaseBalance(50.0);
        Account updatedAccount = accountDatabase.update(account);
        Account fetchedAccount = accountDatabase.get("Alice");

        // Assert
        assertNotNull(updatedAccount);
        assertEquals(150.0, updatedAccount.getBalance());
        assertEquals(updatedAccount, fetchedAccount);
    }

    @Test
    void testCreateMultipleAccounts() {
        // Arrange
        Account account1 = Account.builder().withName("Alice").withBalance(100.0).build();
        Account account2 = Account.builder().withName("Bob").withBalance(200.0).build();

        // Act
        accountDatabase.create(account1);
        accountDatabase.create(account2);

        // Assert
        Account fetchedAccount1 = accountDatabase.get("Alice");
        Account fetchedAccount2 = accountDatabase.get("Bob");

        assertNotNull(fetchedAccount1);
        assertEquals("Alice", fetchedAccount1.getName());
        assertEquals(100.0, fetchedAccount1.getBalance());

        assertNotNull(fetchedAccount2);
        assertEquals("Bob", fetchedAccount2.getName());
        assertEquals(200.0, fetchedAccount2.getBalance());
    }
}
