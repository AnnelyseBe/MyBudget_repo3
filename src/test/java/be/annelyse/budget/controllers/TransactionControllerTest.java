package be.annelyse.budget.controllers;

import be.annelyse.budget.domain.business.model.Transaction;
import be.annelyse.budget.domain.business.service.TransactionService;
import be.annelyse.budget.web.thymeleaf.controllers.TransactionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    TransactionService transactionService;

    @InjectMocks
    TransactionController transactionController;

    Set<Transaction> transactions;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        transactions = new HashSet<>();
        transactions.add(Transaction.builder().id(1L).build());
        transactions.add(Transaction.builder().id(2L).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(transactionController)
                .build();
    }

    @Test
    void listTransactions() throws Exception {
        //todo test werkt niet
/*        when(transactionService.findAll()).thenReturn(transactions);
        mockMvc.perform(MockMvcRequestBuilders.get("/transactions"))
                .andExpect(status().is(200));*/
    }


}