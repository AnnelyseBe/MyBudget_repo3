package be.annelyse.budget.service;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.model.Account;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService extends CrudService<Account, Long> {

    Account findByName(String name);

    List<Account> findAllByNameLike(String name);

    @Transactional
    AccountCommand findCommandById(Long l);

    @Transactional
    AccountCommand saveCommand(AccountCommand command);

    BigDecimal calculateBalanceOfId(Long accountId);



/*

    Account addAccount(String accountName, String accountNumber, String accountNotes);
    Account updateAccount(Long id, String accountName, String accountNumber, String accountNotes, Boolean accountActive);
    Boolean deleteAccountById(Long id);
    List<Account> findAllAccounts();
    List<Account> findActiveAccounts();
    List<Account> findAccountsFilteredByAccountName(String accountNameContains);
    List<Account> findAccountsFilteredByAccountNumber(String accountNumberContains);
    List<Account> findAccountsFilteredByAccountNotes(String accountNotesContains);
    List<Account> findAccountsFilteredByBalance(Integer balanceHigherThan, Integer balanceLowerThan);
    List<Account> filterAccounts(String accountNameContains, String accountNumberContains, String accountNotesContains, Integer balanceHigherThan, Integer balanceLowerThan);
    */
}
