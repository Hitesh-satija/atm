package com.dkatalis.atm.service;

import com.dkatalis.atm.exception.InsufficientBalanceException;
import com.dkatalis.atm.exception.InvalidInputException;
import com.dkatalis.atm.model.Account;
import com.dkatalis.atm.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWithdraw_SufficientBalance() {
        // Arrange
        Account account = Account.builder().withName("Alice").withBalance(50.0).build();
        when(accountRepository.get("Alice")).thenReturn(account);
        when(accountRepository.update(any(Account.class))).thenReturn(account);

        // Act
        Account updatedAccount = transactionService.withdraw("Alice", 50.0);

        // Assert
        assertEquals(50.0, updatedAccount.getBalance());
        verify(accountRepository, times(1)).update(account);
    }

    @Test
    void testWithdraw_InsufficientBalance() {
        // Arrange
    	Account account = Account.builder().withName("Alice").withBalance(30.0).build();
        when(accountRepository.get("Alice")).thenReturn(account);

        // Act & Assert
        assertThrows(InsufficientBalanceException.class,
                () -> transactionService.withdraw("Alice", 50.0));
        verify(accountRepository, never()).update(any(Account.class));
    }

    @Test
    void testDeposit_NoDebt() {
        // Arrange
    	Account account = Account.builder().withName("Alice").withBalance(100.0).build();
        when(accountRepository.get("Alice")).thenReturn(account);
        when(accountRepository.update(any(Account.class))).thenReturn(account);

        // Act
        Account updatedAccount = transactionService.deposit("Alice", 50.0);

        // Assert
        assertEquals(150.0, updatedAccount.getBalance());
        verify(accountRepository, times(1)).update(account);
    }

    @Test
    void testDeposit_WithDebt() {
        // Arrange
    	Account account = Account.builder().withName("Alice").withBalance(100.0).build();
        account.addPayableDebt("Bob", 30.0);
        when(accountRepository.get("Alice")).thenReturn(account);
        when(accountRepository.update(any(Account.class))).thenReturn(account);

        // Act
        Account updatedAccount = transactionService.deposit("Alice", 50.0);

        // Assert
        assertEquals(120.0, updatedAccount.getBalance()); // 50 - 30 = 20 added to balance
        assertEquals(0.0, account.getTotalDebt());
        verify(accountRepository, times(1)).update(account);
    }

    @Test
    void testTransfer_AmountLessThanBalance() {
        // Arrange
        Account sender = Account.builder().withName("Alice").withBalance(100.0).build();
        Account recipient = Account.builder().withName("Bob").withBalance(50.0).build();

        when(accountRepository.get("Alice")).thenReturn(sender);
        when(accountRepository.get("Bob")).thenReturn(recipient);

        // Act
        Account updatedSender = transactionService.transfer("Bob", "Alice", 50.0);

        // Assert
        assertEquals(50.0, updatedSender.getBalance());
        assertEquals(100.0, recipient.getBalance());
        verify(accountRepository, times(1)).update(sender);
        verify(accountRepository, times(1)).update(recipient);
    }

    @Test
    void testTransfer_AmountGreaterThanBalance() {
        // Arrange
    	Account sender = Account.builder().withName("Alice").withBalance(50.0).build();
        Account recipient = Account.builder().withName("Bob").withBalance(50.0).build();

        when(accountRepository.get("Alice")).thenReturn(sender);
        when(accountRepository.get("Bob")).thenReturn(recipient);

        // Act
        Account updatedSender = transactionService.transfer("Bob", "Alice", 100.0);

        // Assert
        assertEquals(0.0, updatedSender.getBalance());
        assertEquals(100.0, recipient.getBalance());
        assertEquals(50.0, sender.getDebtPayable().get("Bob"));
        assertEquals(50.0, recipient.getDebtReceivable().get("Alice"));
        verify(accountRepository, times(1)).update(sender);
        verify(accountRepository, times(1)).update(recipient);
    }

    @Test
    void testTransfer_InvalidRecipient() {
        // Arrange
    	Account sender = Account.builder().withName("Alice").withBalance(50.0).build();
        when(accountRepository.get("Alice")).thenReturn(sender);
        when(accountRepository.get("Bob")).thenReturn(null); // Invalid recipient

        // Act & Assert
        assertThrows(InvalidInputException.class,
                () -> transactionService.transfer("Bob", "Alice", 50.0));
    }

    @Test
    void testRecordDebt() {
        // Arrange
        Account debtee = Account.builder().withName("Alice").withBalance(100.0).build();
        Account debtor = Account.builder().withName("Bob").withBalance(50.0).build();

        // Act
        transactionService.recordDebt(debtee, debtor, 20.0);

        // Assert
        assertEquals(20.0, debtee.getDebtPayable().get("Bob"));
        assertEquals(20.0, debtor.getDebtReceivable().get("Alice"));
    }
}
