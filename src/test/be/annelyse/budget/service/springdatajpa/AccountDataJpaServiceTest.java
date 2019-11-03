package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.commands.converters.AccountCommandToAccount;
import be.annelyse.budget.commands.converters.AccountToAccountCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Transaction;
import be.annelyse.budget.repositories.AccountRepository;
import be.annelyse.budget.service.AccountService;
import be.annelyse.budget.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountDataJpaServiceTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountToAccountCommand accountToAccountCommand;
    @Mock
    AccountCommandToAccount accountCommandToAccount;

    @InjectMocks
    AccountDataJpaService accountService;

    Account returnAccount;
    final Long ACCOUNT_ID = 1L;
    final String ACCOUNT_NAME = "zichtrekening";

    @BeforeEach
    void setUp() {
        returnAccount = (Account.builder().id(ACCOUNT_ID).name(ACCOUNT_NAME).build());
    }

    @Test
    void findAll() {
        Set<Account> returnAccounts = new HashSet<>();
        returnAccounts.add(Account.builder().id(1L).build());
        returnAccounts.add(Account.builder().id(2L).build());

        when(accountRepository.findAll()).thenReturn(returnAccounts);

        assertThat(accountService.findAll(), hasSize(2));
    }

    @Test
    void findById() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(returnAccount));
        Account myAccount = accountService.findById(1L);
        assertThat(myAccount, notNullValue());
    }

    @Test
    void findByIdNotFound() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        Account myAccount = accountService.findById(1L);
        assertThat(myAccount, nullValue());
    }

    @Test
    void save() {
        Account accountToSave = Account.builder().id(ACCOUNT_ID).build();
        when(accountRepository.save(any())).thenReturn(accountToSave);
        Account accountSaved = accountService.save(accountToSave);
        assertThat(accountSaved, equalTo(accountToSave));
    }

    @Test
    void delete() {

        accountService.delete(returnAccount);
        verify(accountRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(returnAccount));

        accountService.deleteById(ACCOUNT_ID);
        verify(accountRepository, times(1)).delete(any());
    }

    @Test
    void findByName() {
        when(accountRepository.findAccountByName(any())).thenReturn(returnAccount);
        Account result = accountService.findByName(ACCOUNT_NAME);
        assertThat(result, equalTo(returnAccount));
        verify(accountRepository).findAccountByName(any());
    }


    @Test
    void calculateBalanceOfId() {
        Account account1 = Account.builder().id(1L).build();
        Account account2 = Account.builder().id(2L).build();

        Transaction transaction1 = Transaction.builder().account(account1).id(11L).validated(true).inflow(BigDecimal.valueOf(100D)).outflow((BigDecimal.valueOf(1000D))).build();
        Transaction transaction2 = Transaction.builder().account(account1).id(12L).validated(true).inflow(BigDecimal.valueOf(100D)).outflow((BigDecimal.valueOf(1000D))).build();
        Transaction transaction3 = Transaction.builder().account(account2).id(13L).validated(true).inflow(BigDecimal.valueOf(5D)).outflow((BigDecimal.valueOf(50D))).build();

        List<Transaction> transactionListAccount1 = new ArrayList<>();
        transactionListAccount1.add(transaction1);
        transactionListAccount1.add(transaction2);
        List<Transaction> transactionListAccount2 = new ArrayList<>();
        transactionListAccount2.add(transaction3);

        account1.setTransactions(transactionListAccount1);
        account2.setTransactions(transactionListAccount2);

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account1));

        BigDecimal balanceTest = BigDecimal.valueOf(0D);
        assertThat(transaction1.getInflow(), equalTo(BigDecimal.valueOf(100D)));
        assertThat(transaction1.getInflow().subtract(transaction1.getOutflow()),equalTo(BigDecimal.valueOf(-900D)));

        BigDecimal balance = accountService.calculateBalanceOfId(1L);

        assertThat(balance, equalTo(BigDecimal.valueOf(-1800D)));
    }
}