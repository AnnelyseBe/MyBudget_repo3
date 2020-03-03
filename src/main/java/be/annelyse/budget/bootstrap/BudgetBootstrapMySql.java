package be.annelyse.budget.bootstrap;

import be.annelyse.budget.model.*;
import be.annelyse.budget.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/*
Initial fill of the database, kan ook via data.sql in de resources,
of via DataLoader implements CommandLineRunner met een run-methode
*/

/*
Hier worden enkele standaard zaken al geïnitialiseerd
*/

@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Component
@Profile({"dev","prod"})
public class BudgetBootstrapMySql implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountService accountService;
    private final CategoryService categoryService;
    private final CostPostService costPostService;
    private final TagService tagService;
    private final TransactionService transactionService;

    public BudgetBootstrapMySql(AccountService accountService, CategoryService categoryService, CostPostService costPostService, TagService tagService, TransactionService transactionService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.costPostService = costPostService;

        this.tagService = tagService;
        this.transactionService = transactionService;
    }

    //contextrefreshedEvent wordt gegooid als applicatiecontext wordt opgebouwd
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.debug("bootstrap dev and prod had started");

        if (accountService.findAll().size() == 0) {
            loadAccounts();
            log.debug("initial accounts are loaded");
        }

        if (categoryService.findAll().size() == 0) {
            loadCategories();
            log.debug("initial categories are loaded");
        }

        if (costPostService.findAll().size() == 0) {
            loadCostPosts();
            log.debug("initial costposts are loaded");
        }

        if (tagService.findAll().size() == 0) {
            loadTags();
            log.debug("initial tags are loaded");
        }

        if (transactionService.findAll().size() == 0) {
            loadTransactions();
            log.debug("initial transactions are loaded");
        }
    }

    private void loadAccounts(){
        log.debug("LoadData started !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Account account1 = new Account();
        account1.setName("DEV-PROD ing zichtrekening");
        account1.setNumber("BE1354354354");
        account1.setDescription("dit is een lange description nnnnn nnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnn");
        account1.setCurrency(Currency.getInstance("EUR"));
        accountService.save(account1);

        Account account2 = new Account();
        account2.setName("DEV-PROD ing spaarrekening");
        account2.setNumber("BE9999999");
        account2.setCurrency(Currency.getInstance("USD"));
        accountService.save(account2);
    }

    private void loadCategories(){
        Category category1 = new Category();
        category1.setName("DEV-PROD kosten");
        categoryService.save(category1);

        Category category2 = new Category();
        category2.setName("DEV-PROD ontspanning");
        categoryService.save(category2);

        Category category3 = new Category();
        category3.setName("DEV-PROD loon");
        categoryService.save(category3);

    }

    private void loadCostPosts(){
    }

    private void loadTags(){
    }

    private void loadTransactions(){
    }
}
