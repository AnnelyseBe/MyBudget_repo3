package be.annelyse.budget.repositories;

import be.annelyse.budget.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//CrudRepository is generisch -> geef type van het domeinobject en datatype van de primary key mee  -> we moeten zelfs geen implementatie hebben van deze interface
//door te extenden van CrudService wordt de repository klasse automatisch gegenereerd ->
//we kunnen ook we kunnen ook afleiden van JpaRepository, dan hebben we nog meer methoden (waaronder ook PagingAndSorting)
//todo ... beslissen of we extenden van CrudRepository of JpaRepository (laatste enkel indien dit nodig blijkt)
public interface AccountRepository extends CrudRepository<Account, Long> {

    //via CTRL-O te kijken welke methodes al bestaan door de interface

    Account findAccountByName(String name);

    List<Account> findAllByNameLike(String name);

}
