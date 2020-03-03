package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.commands.TransactionCommand;
import be.annelyse.budget.commands.converters.TransactionCommandToTransaction;
import be.annelyse.budget.commands.converters.TransactionToTransactionCommand;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
public class TransactionDataJpaService implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionCommandToTransaction transactionCommandToTransaction;
    private final TransactionToTransactionCommand transactionToTransactionCommand;

    public TransactionDataJpaService(TransactionRepository transactionRepository, TransactionCommandToTransaction transactionCommandToTransaction, TransactionToTransactionCommand transactionToTransactionCommand) {
        this.transactionRepository = transactionRepository;
        this.transactionCommandToTransaction = transactionCommandToTransaction;
        this.transactionToTransactionCommand = transactionToTransactionCommand;
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
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
    public List<Transaction> findTransactionsByDescriptionContaining(String description) {
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
    public List<Transaction> findTransactionsByAccountId(Long accountId) {
        //Account account = accountRepository.findById(accountId).orElse(null);
        return findAll().stream()
                .filter(transaction -> transaction.getAccount().getId() == accountId)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionCommand> findCommandsByAccountId(Long accountId) {
        return findTransactionsByAccountId(accountId).stream()
                .map(transactionToTransactionCommand::convert)
                .collect(Collectors.toList());
    }
}
