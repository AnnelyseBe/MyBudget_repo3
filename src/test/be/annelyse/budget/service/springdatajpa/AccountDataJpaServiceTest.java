package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.commands.converters.AccountCommandToAccount;
import be.annelyse.budget.commands.converters.AccountToAccountCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.repositories.AccountRepository;
import be.annelyse.budget.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        accountService.deleteById(ACCOUNT_ID);
        verify(accountRepository, times(1)).deleteById(any());
    }

    @Test
    void findByName() {
        when(accountRepository.findAccountByName(any())).thenReturn(returnAccount);
        Account result = accountService.findByName(ACCOUNT_NAME);
        assertThat(result, equalTo(returnAccount));
        verify(accountRepository).findAccountByName(any());
    }

}