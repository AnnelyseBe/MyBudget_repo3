package be.annelyse.budget.service.map;

import be.annelyse.budget.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapServiceTest {

    AccountMapService accountMapService = new AccountMapService();



    @BeforeEach
    void setUp() {
        Account myAccount = new Account().builder().id(1L).build();
    }

    @Test
    void findAll() {
        //todo implement
    }

    @Test
    void deleteById() {
        //todo implement
    }

    @Test
    void delete() {
        //todo implement
    }

    @Test
    void save() {
        //todo implement
    }

    @Test
    void findById() {
        //todo implement
    }
}