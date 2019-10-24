package be.annelyse.budget.controllers;

import be.annelyse.budget.model.Account;
import be.annelyse.budget.service.AccountService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)    //niet nodig als we initmocks methode gebruiken
class AccountControllerTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountController accountController;

    Set<Account> accounts;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        accounts = new HashSet<>();
        accounts.add(Account.builder().id(1L).build());
        accounts.add(Account.builder().id(2L).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .build();
    }

    @Test
    void listAccounts() throws Exception {
        when(accountService.findAll()).thenReturn(accounts);
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/index"))
                .andExpect(model().attribute("accounts", hasSize(2)));
    }

    @Test
    void findAccounts() throws Exception {

        mockMvc.perform(get("/accounts/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("notimplemented"));
        verifyNoInteractions(accountService);
    }
}