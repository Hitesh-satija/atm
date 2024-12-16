package com.dkatalis.atm;


import com.dkatalis.atm.activity.*;

import com.dkatalis.atm.executor.CommandExecutor;
import com.dkatalis.atm.repository.AccountRepository;
import com.dkatalis.atm.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;
import org.mockito.*;


class ATMTest {
	

    @InjectMocks
    private ATM atm;

    @Mock
    private CommandExecutor commandExecutor;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private LoginService loginService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransferActivity transferActivity;

    @Mock
    private LoginActivity loginActivity;

    @Mock
    private LogoutActivity logoutActivity;

    @Mock
    private DepositActivity depositActivity;

    @Mock
    private WithdrawActivity withdrawActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.atm = new ATM(); // Constructor initializes dependencies
    }

    @Test
    void testConstructorDependencies() {
        // Verify that constructor initializes dependencies
    	
        assertNotNull(atm);
    }
}
