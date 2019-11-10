package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.converters.AccountCommandToAccount;
import be.annelyse.budget.commands.converters.AccountToAccountCommand;
import be.annelyse.budget.exceptions.ActionNotAllowedException;
import be.annelyse.budget.exceptions.NotFoundException;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Transaction;
import be.annelyse.budget.repositories.AccountRepository;
import be.annelyse.budget.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile("springdatajpa")
public class AccountDataJpaService implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountToAccountCommand accountToAccountCommand;
    private final AccountCommandToAccount accountCommandToAccount;

    public AccountDataJpaService(AccountRepository accountRepository, AccountToAccountCommand accountToAccountCommand, AccountCommandToAccount accountCommandToAccount) {
        this.accountRepository = accountRepository;
        this.accountToAccountCommand = accountToAccountCommand;
        this.accountCommandToAccount = accountCommandToAccount;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (!accountOptional.isPresent()) {
            throw new NotFoundException("Account with id: " + id + " not found !!!");
        }
        return accountOptional.get();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account inactivateAccount(Account account){
        account.setActive(false);
        return save(account);
    }

    @Override
    public void delete(Account accountToDelete) {
        if (accountToDelete == null){
            throw new NotFoundException("the account to be deleted does not exist");
        }
        else if (accountToDelete.getTransactions() == null || accountToDelete.getTransactions().isEmpty()){
            accountRepository.delete(accountToDelete);
        }
        else {
            inactivateAccount(accountToDelete);
            throw new ActionNotAllowedException("an account with transaction coupled to it can not be deleted. It has been invalidated instead");
        }
    }

    @Override
    public void deleteById(Long id) {
        Account accountToDelete = findById(id);
        delete(accountToDelete);
    }

    @Override
    public Account findByName(String name) {
        return accountRepository.findAccountByName(name);
    }

    @Override
    public List<Account> findAllByNameLike(String name) {
        return accountRepository.findAllByNameLike(name);
    }

    @Override
    @Transactional
    public AccountCommand findCommandById(Long id) {
        return accountToAccountCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public AccountCommand saveCommand(AccountCommand command) {
        Account detachedAccount = accountCommandToAccount.convert(command);

        Account savedAccount = accountRepository.save(detachedAccount);
        log.debug("Saved AccountId:" + savedAccount.getId());
        return accountToAccountCommand.convert(savedAccount);
    }

    @Override
    public BigDecimal calculateBalanceOfId(Long accountId) {
        Account myAccount = findById(accountId);
        List<Transaction> transactionsByAccountId = myAccount.getTransactions();

        BigDecimal balance = new BigDecimal("0");

        for (int i = 0; i < transactionsByAccountId.size(); i++) {
            if (transactionsByAccountId.get(i).getValidated()) {
                balance = balance.subtract(transactionsByAccountId.get(i).getOutflow()).add(transactionsByAccountId.get(i).getInflow());
            }
        }
        return balance;
    }

    @Override
    public Set<Currency> getCurrenciesToChoose() {
        Set<Currency> currenciesToChoose = new HashSet<>();
        currenciesToChoose.add(Currency.getInstance("EUR"));
        currenciesToChoose.add(Currency.getInstance("USD"));
        return currenciesToChoose;
    }

}
