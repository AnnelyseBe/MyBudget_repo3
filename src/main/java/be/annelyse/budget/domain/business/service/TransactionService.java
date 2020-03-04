package be.annelyse.budget.domain.business.service;

import be.annelyse.budget.web.dto.TransactionDto;
import be.annelyse.budget.domain.business.model.Transaction;

import java.util.List;


public interface TransactionService extends CrudService<Transaction, Long> {


    List<Transaction> findTransactionsByDescriptionContaining(String description);

    TransactionDto findCommandById(Long id);

    TransactionDto saveCommand(TransactionDto command);

    List<Transaction> findTransactionsByAccountId(Long accountId);

    List<TransactionDto> findCommandsByAccountId(Long accountId);

}
