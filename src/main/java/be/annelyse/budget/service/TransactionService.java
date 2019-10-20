package be.annelyse.budget.service;

import be.annelyse.budget.model.Transaction;

import java.util.Set;


public interface TransactionService extends CrudService<Transaction, Long> {


    Set<Transaction> findTransactionByDescriptionContaining(String description);


}
