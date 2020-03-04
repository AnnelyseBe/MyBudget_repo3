package be.annelyse.budget.web.rest.controllers;

import be.annelyse.budget.domain.business.model.Account;
import be.annelyse.budget.domain.business.service.AccountService;
import be.annelyse.budget.web.dto.AccountDto;
import be.annelyse.budget.web.dto.AccountListDto;
import be.annelyse.budget.web.mappers.AccountDtoToAccount;
import be.annelyse.budget.web.mappers.AccountToAccountDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(description="This is the REST controller for the accounts")
@RestController
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@RequestMapping(AccountRestController.BASE_URL)
public class AccountRestController {

    public static final String BASE_URL = "/rest/accounts";

    private final AccountService accountService;
    private final AccountToAccountDto accountToDto;
    private final AccountDtoToAccount dtoToAccount;

    public AccountRestController(AccountService accountService, AccountToAccountDto accountTodto, AccountDtoToAccount dtoToAccount) {
        this.accountService = accountService;
        this.accountToDto = accountTodto;
        this.dtoToAccount = dtoToAccount;
    }

    @ApiOperation(value="Find By Name Like", notes="Geeft een stuk tekst en je krijgt alle namen waar dat stuk tekst in voorkomt")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public AccountListDto findByNameLike(@RequestParam String nameLike) {
        List<AccountDto> accountDtoList = accountService.findAllByNameLike(nameLike).stream()
                .map(accountToDto::convert)
                .collect(Collectors.toList());
        return new AccountListDto(accountDtoList);
    }

    @ApiOperation(value="Create New Account", notes="Een nieuw account creëren")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createNewAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.save(dtoToAccount.convert(accountDto));
        return accountToDto.convert(account);
    }

    @ApiOperation(value="Update Account", notes="Een account updaten")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public AccountDto updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        Account account = accountService.update(id, dtoToAccount.convert(accountDto));
        return accountToDto.convert(account);
    }

    @ApiOperation(value="Patch Account", notes="Enkele velden van Account aanpassen")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public AccountDto patchAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        Account account = accountService.patch(id, dtoToAccount.convert(accountDto));
        return accountToDto.convert(account);
    }

    @ApiOperation(value="Delete Account", notes="Het account verwijderen dmv zijn id")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
    }
}
