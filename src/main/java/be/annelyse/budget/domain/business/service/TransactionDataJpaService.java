package be.annelyse.budget.domain.business.service;

import be.annelyse.budget.web.dto.TransactionDto;
import be.annelyse.budget.web.mappers.TransactionDtoToTransaction;
import be.annelyse.budget.web.mappers.TransactionToTransactionDto;
import be.annelyse.budget.domain.business.model.Transaction;
import be.annelyse.budget.domain.dao.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
public class TransactionDataJpaService implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDtoToTransaction transactionDtoToTransaction;
    private final TransactionToTransactionDto transactionToTransactionDto;

    public TransactionDataJpaService(TransactionRepository transactionRepository, TransactionDtoToTransaction transactionDtoToTransaction, TransactionToTransactionDto transactionToTransactionDto) {
        this.transactionRepository = transactionRepository;
        this.transactionDtoToTransaction = transactionDtoToTransaction;
        this.transactionToTransactionDto = transactionToTransactionDto;
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
    public TransactionDto findCommandById(Long id) {
        return transactionToTransactionDto.convert(findById(id));
    }

    @Override
    @Transactional
    public TransactionDto saveCommand(TransactionDto command) {
        Transaction detachedTransaction = transactionDtoToTransaction.convert(command);

        Transaction savedTransaction = transactionRepository.save(detachedTransaction);
        log.debug("Saved TransactionId:" + savedTransaction.getId());
        return transactionToTransactionDto.convert(savedTransaction);
    }

    @Override
    public List<Transaction> findTransactionsByAccountId(Long accountId) {
        //Account account = accountRepository.findById(accountId).orElse(null);
        return findAll().stream()
                .filter(transaction -> transaction.getAccount().getId() == accountId)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findCommandsByAccountId(Long accountId) {
        return findTransactionsByAccountId(accountId).stream()
                .map(transactionToTransactionDto::convert)
                .collect(Collectors.toList());
    }
}
