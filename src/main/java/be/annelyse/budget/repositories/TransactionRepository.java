package be.annelyse.budget.repositories;

import be.annelyse.budget.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

/*    Set<Transaction> findTransactionByDescriptionContaining(String description);
    Set<Transaction> findTransactionByDescriptionContainingIgnoreCase(String description);*/

    /*    Nieuwe methoden definieren -> zie boek Spring p 140*/
    List<Transaction> findTransactionByDescriptionIgnoreCaseContaining(String description);


}
