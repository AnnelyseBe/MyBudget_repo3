package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.converters.AccountCommandToAccount;
import be.annelyse.budget.commands.converters.AccountToAccountCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.repositories.AccountRepository;
import be.annelyse.budget.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public AccountDataJpaService(AccountRepository accountRepository, AccountToAccountCommand accountToAccountCommand, AccountCommandToAccount accountCommandToAccount) {
        this.accountRepository = accountRepository;
        this.accountToAccountCommand = accountToAccountCommand;
        this.accountCommandToAccount = accountCommandToAccount;
    }

    @Override
    public Set<Account> findAll() {
        Set<Account> accounts = new HashSet<>();
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

}
