package be.annelyse.budget.controllers;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)    //niet nodig als we initmocks methode gebruiken
class AccountControllerTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    AccountController accountController;

    List<Account> accounts;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        accounts = new ArrayList<>();
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

    @Test
    void showById() throws Exception {
        Account account = new Account();
        account.setId(3L);

        when(accountService.findById(anyLong())).thenReturn(account);

        mockMvc.perform(get("/accounts/3/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/show"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    void updateAccount() throws Exception {
        AccountCommand command = new AccountCommand();
        command.setId(5L);

        when(accountService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/accounts/3/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/form"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    void saveOrUpdate() throws Exception {

        AccountCommand command = new AccountCommand();
        command.setId(10L);

        when(accountService.saveCommand(any())).thenReturn(command);

        mockMvc.perform(post("/accounts/saveOrUpdate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/accounts/10"));
    }


    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/accounts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/accounts"));

        verify(accountService,times(1)).deleteById(anyLong());
    }
}