package be.annelyse.budget.repositories;

import be.annelyse.budget.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository is generisch -> geef type van het domeinobject en datatype van de primary key mee  -> we moeten zelfs geen implementatie hebben van deze interface
public interface AccountRepository extends JpaRepository<Account, Long> {


    //via CTRL-O te kijken welke methodes al bestaan door de interface
    //save(T entity)
    //findById(ID id) -> Optional<Account> teruggegeven
    //findAll() -> List<Account> als return
    //deleteById(ID id)
    //delete(T entity)
    //deleteAll()
    //existsById(ID id)
}
