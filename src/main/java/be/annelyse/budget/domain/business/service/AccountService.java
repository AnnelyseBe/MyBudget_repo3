package be.annelyse.budget.domain.business.service;

import be.annelyse.budget.web.dto.AccountDto;
import be.annelyse.budget.domain.business.model.Account;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Set;

public interface AccountService extends CrudService<Account, Long> {

    Account findByName(String name);

    List<Account> findAllByNameLike(String name);

    Account update(Long id, Account account);

    Account patch(Long id, Account account);

    Account inactivateAccount(Account account);

    @Transactional
    AccountDto findCommandById(Long l);

    @Transactional
    AccountDto saveCommand(AccountDto command);

    BigDecimal calculateBalanceOfId(Long accountId);

    Set<Currency> getCurrenciesToChoose();



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
