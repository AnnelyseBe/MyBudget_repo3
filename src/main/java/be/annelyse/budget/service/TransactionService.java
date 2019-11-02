package be.annelyse.budget.service;

import be.annelyse.budget.commands.TransactionCommand;
import be.annelyse.budget.model.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


public interface TransactionService extends CrudService<Transaction, Long> {


    Set<Transaction> findTransactionsByDescriptionContaining(String description);

    TransactionCommand findCommandById(Long id);

    TransactionCommand saveCommand(TransactionCommand command);

    Set<TransactionCommand> findCommandsByAccountId(Long accountId);

}
