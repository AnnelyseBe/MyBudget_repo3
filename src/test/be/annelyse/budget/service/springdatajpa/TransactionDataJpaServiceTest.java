package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.commands.TransactionCommand;
import be.annelyse.budget.commands.converters.TransactionCommandToTransaction;
import be.annelyse.budget.commands.converters.TransactionToTransactionCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Transaction;
import be.annelyse.budget.repositories.TransactionRepository;
import be.annelyse.budget.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class TransactionDataJpaServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AccountService accountService;

    @Mock
    TransactionToTransactionCommand transactionToTransactionCommand;

    @Mock
    TransactionCommandToTransaction transactionCommandToTransaction;

    @InjectMocks
    TransactionDataJpaService service;

    @BeforeEach
    void setUp() {
        service = new TransactionDataJpaService(transactionRepository, accountService, transactionCommandToTransaction, transactionToTransactionCommand);
    }

    @Test
    void findTransactionByDescriptionContaining() {

        Transaction returnTransaction = Transaction.builder().description("AnneLieS haar Knutselgerief").id(1L).build();
        Set<Transaction> returnSet = new HashSet<>();
        returnSet.add(returnTransaction);

        when(transactionRepository.findTransactionByDescriptionIgnoreCaseContaining(anyString())).thenReturn(returnSet);

        Set<Transaction> result = service.findTransactionsByDescriptionContaining("annelies");

        assertThat(result, hasSize(1));
        assertThat(result, contains(returnTransaction));
    }

    @Test
    void findCommandsByAccountId() {
        Account account1 = Account.builder().id(11L).build();
        Account account2 = Account.builder().id(12L).build();

        Transaction transaction1 = Transaction.builder().description("transaction1").id(1L).account(account1).build();
        Transaction transaction2 = Transaction.builder().description("transaction2").id(2L).account(account1).build();
        Transaction transaction3 = Transaction.builder().description("transaction3").id(3L).account(account2).build();

        TransactionCommand transactionCommand1 = new TransactionCommand();
        TransactionCommand transactionCommand2 = new TransactionCommand();

        Set<Transaction> transactionSet = new HashSet<>();
        transactionSet.add(transaction1);
        transactionSet.add(transaction2);
        transactionSet.add(transaction3);

        when(accountService.findById(anyLong())).thenReturn(account1);
        when(transactionRepository.findAll()).thenReturn(transactionSet);
        when(transactionToTransactionCommand.convert(transaction1)).thenReturn(transactionCommand1);
        when(transactionToTransactionCommand.convert(transaction2)).thenReturn(transactionCommand2);

        Set<TransactionCommand> result = service.findCommandsByAccountId(11L);

        assertThat(result, hasSize(2));
        //let op ... containsInAnyOrder moet alle items van de collection bevatten -> containsInAnyOrder(transaction1) geeft false
        assertThat(result, containsInAnyOrder(transactionCommand1, transactionCommand2));
    }
}