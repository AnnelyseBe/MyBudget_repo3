package be.annelyse.budget.domain.dao;

import be.annelyse.budget.domain.business.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    //via CTRL-O te kijken welke methodes al bestaan door de interface

    Account findAccountByName(String name);

    List<Account> findAllByNameLike(String name);

}
