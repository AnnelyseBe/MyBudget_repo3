package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.model.Transaction;
import be.annelyse.budget.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class TransactionDataJpaServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionDataJpaService service;

    @BeforeEach
    void setUp() {
        service = new TransactionDataJpaService(transactionRepository);
    }

    @Test
    void findTransactionByDescriptionContaining() {

        Transaction returnTransaction = Transaction.builder().description("AnneLieS haar Knutselgerief").id(1L).build();
        Set<Transaction> returnSet = new HashSet<>();
        returnSet.add(returnTransaction);

        when(transactionRepository.findTransactionByDescriptionIgnoreCaseContaining(anyString())).thenReturn(returnSet);

        Set<Transaction> result = service.findTransactionByDescriptionContaining("annelies");

        assertThat(result, hasSize(1));
        assertThat(result, contains(returnTransaction));
    }
}