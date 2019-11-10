package be.annelyse.budget.controllers;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.exceptions.NotFoundException;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.*;
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

    private List<Account> accounts;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        accounts = new ArrayList<>();
        accounts.add(Account.builder().id(1L).build());
        accounts.add(Account.builder().id(2L).build());
        accounts.add(Account.builder().id(3L).build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .build();
    }

    @Test
    void findAccounts() throws Exception {

        mockMvc.perform(get("/accounts/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/findAccounts"));
        verifyNoInteractions(accountService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(accountService.findAllByNameLike(anyString()))
                .thenReturn(Arrays.asList(
                        Account.builder().id(1L).build(),
                        Account.builder().id(2L).build()
                ));

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/accountsList"))
                .andExpect(model().attribute("accounts", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        when(accountService.findAllByNameLike(anyString())).thenReturn(Arrays.asList(Account.builder().id(1l).build()));

        mockMvc.perform(get("/accounts"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/accounts/1"));
    }

    @Test
    void processFindFormEmptyReturnMany() throws Exception {
        when(accountService.findAllByNameLike(anyString()))
                .thenReturn(Arrays.asList(
                        Account.builder().id(1L).build(),
                        Account.builder().id(2L).build()
                ));

        mockMvc.perform(get("/accounts")
                .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/accountsList"))
                .andExpect(model().attribute("accounts", hasSize(2)));

    }

    @Test
    void showById_happyPath() throws Exception {
        Account account = new Account();
        account.setId(3L);

        when(accountService.findById(anyLong())).thenReturn(account);

        mockMvc.perform(get("/accounts/3"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/accountDetails"))
                .andExpect(model().attribute("account", hasProperty("id", is(3L))));
    }

    @Test
    void showById_NumberFormatException() throws Exception {
        mockMvc.perform(get("/accounts/ddfd"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("errors/400Error"));
    }

    @Test
    void showById_notFound() throws Exception {

        when(accountService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/accounts/3"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("errors/404Error"));
    }

    @Test
    void initCreationForm() throws Exception{
        mockMvc.perform(get("/accounts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/createOrUpdateAccountForm"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    void processCreationForm() throws Exception {
        when(accountService.save(ArgumentMatchers.any())).thenReturn(Account.builder().id(1L).build());

        mockMvc.perform(post("/accounts/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/accounts/1"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    void initUpdateAccountForm() throws Exception{
       when(accountService.findById(ArgumentMatchers.any())).thenReturn(Account.builder().id(1L).build());
       when(accountService.getCurrenciesToChoose()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/accounts/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("accounts/createOrUpdateAccountForm"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("currenciesToChoose"));
    }

    @Test
    void processUpdatedAccountForm() throws Exception {
        when(accountService.save(ArgumentMatchers.any())).thenReturn(Account.builder().id(3L).build());

        mockMvc.perform(post("/accounts/3/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/accounts/3"))
                .andExpect(model().attributeExists("account"));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/accounts/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/accounts"));

        verify(accountService,times(1)).deleteById(anyLong());
    }



}