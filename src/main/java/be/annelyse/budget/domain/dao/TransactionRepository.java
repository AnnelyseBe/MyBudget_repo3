package be.annelyse.budget.domain.dao;

import be.annelyse.budget.domain.business.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

/*    Set<Transaction> findTransactionByDescriptionContaining(String description);
    Set<Transaction> findTransactionByDescriptionContainingIgnoreCase(String description);*/

    /*    Nieuwe methoden definieren -> zie boek Spring p 140*/
    List<Transaction> findTransactionByDescriptionIgnoreCaseContaining(String description);


}
