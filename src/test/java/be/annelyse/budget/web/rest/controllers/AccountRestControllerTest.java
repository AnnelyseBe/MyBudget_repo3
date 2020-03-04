package be.annelyse.budget.web.rest.controllers;

import be.annelyse.budget.domain.business.exceptions.ActionNotAllowedException;
import be.annelyse.budget.domain.business.model.Account;
import be.annelyse.budget.domain.business.service.AccountService;
import be.annelyse.budget.web.dto.AccountDto;
import be.annelyse.budget.web.mappers.AccountDtoToAccount;
import be.annelyse.budget.web.mappers.AccountToAccountDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountRestControllerTest extends AbstractRestControllerTest {

    AccountService accountService = Mockito.mock(AccountService.class);

    AccountToAccountDto accountToAccountDto = new AccountToAccountDto();

    AccountDtoToAccount accountDtoToAccount = new AccountDtoToAccount();

    AccountRestController accountRestController = new AccountRestController(accountService, accountToAccountDto, accountDtoToAccount);

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(accountRestController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void patchAccount() throws Exception {
        //given
        AccountDto accountDto = new AccountDto();
        accountDto.setName("ING zichtrekening");

        Account returnAccount = new Account();
        returnAccount.setName(accountDto.getName());
        returnAccount.setActive(true);
        returnAccount.setDescription("This is my ING zichtrekening");

        when(accountService.patch(anyLong(), any(Account.class))).thenReturn(returnAccount);

        mockMvc.perform(patch(AccountRestController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("ING zichtrekening")))
                .andExpect(jsonPath("$.description", equalTo("This is my ING zichtrekening")))
                .andExpect(jsonPath("$.active", equalTo(true)));
    }

    @Test
    void DeleteAccount() throws Exception {
        //given
        AccountDto accountDto = new AccountDto();
        accountDto.setName("ING zichtrekening");

        //when
        when(accountService.patch(anyLong(), any(Account.class))).thenThrow(ActionNotAllowedException.class);

        mockMvc.perform(patch(AccountRestController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountDto)))
                .andExpect(status().isNotAcceptable());
    }

}