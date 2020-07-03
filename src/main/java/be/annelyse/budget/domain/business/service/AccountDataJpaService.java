package be.annelyse.budget.domain.business.service;

import be.annelyse.budget.domain.business.exceptions.ActionNotAllowedException;
import be.annelyse.budget.domain.business.exceptions.NotFoundException;
import be.annelyse.budget.domain.business.model.Account;
import be.annelyse.budget.domain.business.model.Transaction;
import be.annelyse.budget.domain.dao.AccountRepository;
import be.annelyse.budget.web.dto.AccountDto;
import be.annelyse.budget.web.mappers.AccountDtoToAccount;
import be.annelyse.budget.web.mappers.AccountToAccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
public class AccountDataJpaService implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountToAccountDto accountToAccountDto;
    private final AccountDtoToAccount accountDtoToAccount;

    public AccountDataJpaService(AccountRepository accountRepository, AccountToAccountDto accountToAccountDto, AccountDtoToAccount accountDtoToAccount) {
        this.accountRepository = accountRepository;
        this.accountToAccountDto = accountToAccountDto;
        this.accountDtoToAccount = accountDtoToAccount;
    }

    @Override
    public List<Account> findAll() {
        //todo delete maar , dit is voor mobbing test
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public Account findById(Long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        if (accountOptional.isEmpty()) {
            throw new NotFoundException("Account with id: " + id + " not found !!!");
        }
        return accountOptional.get();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Long id, Account account) {
        account.setId(id);
        return accountRepository.save(account);
    }

    @Override
    public Account patch(Long id, Account account) {
        return accountRepository.findById(id).map(accountToPatch -> {

            if (account.getName() != null) {
                accountToPatch.setName(account.getName());
            }
            if (account.getCurrency() != null) {
                accountToPatch.setCurrency(account.getCurrency());
            }
            if (account.getDescription() != null) {
                accountToPatch.setDescription(account.getDescription());
            }
            if (account.getNumber() != null) {
                accountToPatch.setNumber(account.getNumber());
            }
            if (account.getActive() != null) {
                accountToPatch.setActive(account.getActive());
            }
            return accountRepository.save(accountToPatch);
        }).orElseThrow(RuntimeException::new); //todo implement better exception handling;
    }

    @Override
    public Account inactivateAccount(Account account) {
        account.setActive(false);
        return save(account);
    }

    @Override
    public void delete(Account accountToDelete) {
        if (accountToDelete == null) {
            throw new NotFoundException("the account to be deleted does not exist");
        } else if (accountToDelete.getTransactions() == null || accountToDelete.getTransactions().isEmpty()) {
            accountRepository.delete(accountToDelete);
        } else {
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
        return accountRepository.findAllByNameLike("%" + name + "%");
    }

    @Override
    @Transactional
    public AccountDto findCommandById(Long id) {


        return accountToAccountDto.convert(findById(id));

    }

    @Override
    @Transactional
    public AccountDto saveCommand(AccountDto command) {
        Account detachedAccount = accountDtoToAccount.convert(command);

        Account savedAccount = accountRepository.save(detachedAccount);
        log.debug("Saved AccountId:" + savedAccount.getId());
        return accountToAccountDto.convert(savedAccount);
    }

    @Override
    public BigDecimal calculateBalanceOfId(Long accountId) {
        Account myAccount = findById(accountId);List<Transaction> transactionsByAccountId = myAccount.getTransactions();

        BigDecimal balance = new BigDecimal("0");

        for (Transaction transaction : transactionsByAccountId) {

            if (transaction.getValidated()) {

                balance = balance.subtract(transaction.getOutflow()).add(transaction.getInflow());




            }
        }
        return balance;
    }

    @Override
    public Set<Currency> getCurrenciesToChoose() {
        Set<Currency> currenciesToChoose = new HashSet<>();

        currenciesToChoose.add





                (Currency.getInstance("EUR"));


        currenciesToChoose.add(Currency.getInstance("USD"));
        return currenciesToChoose;
    }

}
