package be.annelyse.budget.domain.business.service.springdatajpa;

import be.annelyse.budget.domain.business.service.TransactionDataJpaService;
import be.annelyse.budget.web.dto.TransactionDto;
import be.annelyse.budget.web.mappers.TransactionDtoToTransaction;
import be.annelyse.budget.web.mappers.TransactionToTransactionDto;
import be.annelyse.budget.domain.business.model.Account;
import be.annelyse.budget.domain.business.model.Transaction;
import be.annelyse.budget.domain.dao.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class TransactionDataJpaServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionToTransactionDto transactionToTransactionDto;

    @Mock
    TransactionDtoToTransaction transactionDtoToTransaction;

    @InjectMocks
    TransactionDataJpaService service;

    @BeforeEach
    void setUp() {
        service = new TransactionDataJpaService(transactionRepository, transactionDtoToTransaction, transactionToTransactionDto);
    }

    @Test
    void findTransactionByDescriptionContaining() {

        Transaction returnTransaction = Transaction.builder().description("AnneLieS haar Knutselgerief").id(1L).build();
        List<Transaction> returnSet = new ArrayList<>();
        returnSet.add(returnTransaction);

        when(transactionRepository.findTransactionByDescriptionIgnoreCaseContaining(anyString())).thenReturn(returnSet);

        List<Transaction> result = service.findTransactionsByDescriptionContaining("annelies");

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

        TransactionDto transactionDto1 = new TransactionDto();
        TransactionDto transactionDto2 = new TransactionDto();

        List<Transaction> transactionSet = new ArrayList<>();
        transactionSet.add(transaction1);
        transactionSet.add(transaction2);
        transactionSet.add(transaction3);

        when(transactionRepository.findAll()).thenReturn(transactionSet);
        when(transactionToTransactionDto.convert(transaction1)).thenReturn(transactionDto1);
        when(transactionToTransactionDto.convert(transaction2)).thenReturn(transactionDto2);

        List<TransactionDto> result = service.findCommandsByAccountId(11L);

        assertThat(result, hasSize(2));
        //let op ... containsInAnyOrder moet alle items van de collection bevatten -> containsInAnyOrder(transaction1) geeft false
        assertThat(result, containsInAnyOrder(transactionDto1, transactionDto2));
    }
}