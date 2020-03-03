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

@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Component
@Profile("default")
public class BudgetBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountService accountService;
    private final CategoryService categoryService;
    private final CostPostService costPostService;
    private final TagService tagService;
    private final TransactionService transactionService;

    public BudgetBootstrap(AccountService accountService, CategoryService categoryService, CostPostService costPostService, TagService tagService, TransactionService transactionService) {
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

        log.debug("bootstrap default (H2) has started");

        int count = accountService.findAll().size();
        if (count == 0) {
            loadData();
            log.debug("Loading Bootstrap Data");
        }
    }

    private void loadData() {

        log.debug("LoadData started !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Account account1 = new Account();
        account1.setName("H2-ing zichtrekening");
        account1.setNumber("BE1354354354");
        account1.setDescription("dit is een lange description nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnnnnnnnnnnnnnnnnnnnn nnnnnnnnn");
        account1.setCurrency(Currency.getInstance("EUR"));
        accountService.save(account1);

        Account account2 = new Account();
        account2.setName("H2-ing spaarrekening");
        account2.setNumber("H2-BE9999999");
        account2.setCurrency(Currency.getInstance("USD"));
        accountService.save(account2);

        Category category1 = new Category();
        category1.setName("H2-kosten");
        categoryService.save(category1);

        Category category2 = new Category();
        category2.setName("H2-ontspanning");
        categoryService.save(category2);

        Category category3 = new Category();
        category3.setName("H2-loon");
        categoryService.save(category3);

        CostPost costPost1 = new CostPost();
        costPost1.setCategory(category1);
        costPost1.setName("H2-boodschappen");
        costPost1.setFlow(FlowType.EXPENSE);
        costPostService.save(costPost1);

        CostPost costPost2 = new CostPost();
        costPost2.setCategory(category2);
        costPost2.setName("H2-activiteiten");
        costPost2.setFlow(FlowType.EXPENSE);
        costPostService.save(costPost2);

        CostPost costPost3 = new CostPost();
        costPost3.setCategory(category3);
        costPost3.setName("H2-loon annelies");
        costPost3.setFlow(FlowType.INCOME);
        costPostService.save(costPost3);

        Tag tag1 = new Tag();
        tag1.setName("H2-spar");
        tagService.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("H2-lopenAnnelies");
        tagService.save(tag2);

        Transaction transaction1 = new Transaction();
        transaction1.setAccount(account1);
        transaction1.setOutflow(new BigDecimal(125D));
        transaction1.setDate(LocalDate.of(2019, 9, 22));
        transaction1.setDescription("H2-sportevenement");
        transaction1.setRecurring(Recurring.NONE);
        transaction1.setCostPost(costPost2);
        transaction1.setValidated(true);
        transaction1.addTag(tag2);
        transactionService.save(transaction1);

        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        Transaction transaction2 = new Transaction();
        transaction2.setAccount(account2);
        transaction2.setOutflow(new BigDecimal(250D));
        transaction2.setDate(LocalDate.of(2019, 9, 28));
        transaction2.setDescription("H2-spar");
        transaction2.setRecurring(Recurring.NONE);
        transaction2.setCostPost(costPost1);
        transaction2.setValidated(true);
        transaction2.setTags(tags);
        transactionService.save(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setAccount(account1);
        transaction3.setInflow(new BigDecimal(1000D));
        transaction3.setDate(LocalDate.of(2019, 10, 1));
        transaction3.setDescription("H2-mijn loon");
        transaction3.setRecurring(Recurring.EVERY_MONTH);
        transaction3.setCostPost(costPost3);
        transaction3.setValidated(true);
        transactionService.save(transaction3);

        log.debug("initial data is loaded");
    }
}
