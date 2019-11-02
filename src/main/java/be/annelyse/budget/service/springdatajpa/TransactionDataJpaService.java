package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.TransactionCommand;
import be.annelyse.budget.commands.converters.TransactionCommandToTransaction;
import be.annelyse.budget.commands.converters.TransactionToTransactionCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Transaction;
import be.annelyse.budget.repositories.TransactionRepository;
import be.annelyse.budget.service.AccountService;
import be.annelyse.budget.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile("springdatajpa")
public class TransactionDataJpaService implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionCommandToTransaction transactionCommandToTransaction;
    private final TransactionToTransactionCommand transactionToTransactionCommand;

    public TransactionDataJpaService(TransactionRepository transactionRepository, AccountService accountService, TransactionCommandToTransaction transactionCommandToTransaction, TransactionToTransactionCommand transactionToTransactionCommand) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionCommandToTransaction = transactionCommandToTransaction;
        this.transactionToTransactionCommand = transactionToTransactionCommand;
    }

    @Override
    public Set<Transaction> findAll() {
        Set<Transaction> transactions = new HashSet<>();
        transactionRepository.findAll().forEach(transactions::add);
        return transactions;
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void delete(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Set<Transaction> findTransactionsByDescriptionContaining(String description) {
        return this.transactionRepository.findTransactionByDescriptionIgnoreCaseContaining(description);
    }

    @Override
    @Transactional
    public TransactionCommand findCommandById(Long id) {
        return transactionToTransactionCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public TransactionCommand saveCommand(TransactionCommand command) {
        Transaction detachedTransaction = transactionCommandToTransaction.convert(command);

        Transaction savedTransaction = transactionRepository.save(detachedTransaction);
        log.debug("Saved TransactionId:" + savedTransaction.getId());
        return transactionToTransactionCommand.convert(savedTransaction);
    }

    @Override
    public Set<TransactionCommand> findCommandsByAccountId(Long accountId) {
        Account account = accountService.findById(accountId);
        return findAll().stream()
                .filter(transaction -> transaction.getAccount() == account )
                .map(transactionToTransactionCommand::convert)
                .collect(Collectors.toSet());
    }
}
