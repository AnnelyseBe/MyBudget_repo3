package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.converters.AccountCommandToAccount;
import be.annelyse.budget.commands.converters.AccountToAccountCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Transaction;
import be.annelyse.budget.repositories.AccountRepository;
import be.annelyse.budget.repositories.TransactionRepository;
import be.annelyse.budget.service.AccountService;
import be.annelyse.budget.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile("springdatajpa")
public class AccountDataJpaService implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountToAccountCommand accountToAccountCommand;
    private final AccountCommandToAccount accountCommandToAccount;
    private final TransactionRepository transactionRepository;

    public AccountDataJpaService(AccountRepository accountRepository, AccountToAccountCommand accountToAccountCommand, AccountCommandToAccount accountCommandToAccount, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.accountToAccountCommand = accountToAccountCommand;
        this.accountCommandToAccount = accountCommandToAccount;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(Account object) {
        accountRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
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

}
