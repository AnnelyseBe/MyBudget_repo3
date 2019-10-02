package be.annelyse.budget.bootstrap;

import be.annelyse.budget.model.*;
import be.annelyse.budget.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Component
public class DataLoader implements CommandLineRunner {

    private final AccountService accountService;
    private final CategoryService categoryService;
    private final CostPostService costPostService;
    private final TagService tagService;
    private final TransactionService transactionService;

    @Autowired
    public DataLoader(AccountService accountService, CategoryService categoryService, CostPostService costPostService, TagService tagService, TransactionService transactionService) {
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.costPostService = costPostService;
        this.tagService = tagService;
        this.transactionService = transactionService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = accountService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {

        System.out.println("LoadData started !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Account account1 = new Account();
        account1.setName("ing zichtrekening");
        account1.setNumber("BE1354354354");
        accountService.save(account1);

        Account account2 = new Account();
        account2.setName("ing spaarrekening");
        account2.setNumber("BE9999999");
        accountService.save(account2);

        Category category1 = new Category();
        category1.setName("kosten");
        categoryService.save(category1);

        Category category2 = new Category();
        category2.setName("ontspanning");
        categoryService.save(category2);

        Category category3 = new Category();
        category3.setName("loon");
        categoryService.save(category3);

        CostPost costPost1 = new CostPost();
        costPost1.setCategory(category1);
        costPost1.setName("boodschappen");
        costPost1.setFlow(FlowType.EXPENSE);
        costPostService.save(costPost1);

        CostPost costPost2 = new CostPost();
        costPost2.setCategory(category2);
        costPost2.setName("activiteiten");
        costPost2.setFlow(FlowType.EXPENSE);
        costPostService.save(costPost2);

        CostPost costPost3 = new CostPost();
        costPost3.setCategory(category3);
        costPost3.setName("loon annelies");
        costPost3.setFlow(FlowType.INCOME);
        costPostService.save(costPost3);

        Tag tag1 = new Tag();
        tag1.setName("spar");
        tagService.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("lopenAnnelies");
        tagService.save(tag2);

        Transaction transaction1 = new Transaction();
        transaction1.setOutflow(new BigDecimal(125D));
        transaction1.setDate(LocalDate.of(2019, 9, 22));
        transaction1.setDescription("sportevenement");
        transaction1.setRecurring(Recurring.NONE);
        transaction1.setCostPost(costPost2);
        transaction1.setValidated(true);
        transaction1.addTag(tag2);
        transactionService.save(transaction1);

        Set<Tag> tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);

        Transaction transaction2 = new Transaction();
        transaction2.setOutflow(new BigDecimal(250D));
        transaction2.setDate(LocalDate.of(2019, 9, 28));
        transaction2.setDescription("spar");
        transaction2.setRecurring(Recurring.NONE);
        transaction2.setCostPost(costPost1);
        transaction2.setValidated(true);
        transaction2.setTags(tags);
        transactionService.save(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setInflow(new BigDecimal(1000D));
        transaction3.setDate(LocalDate.of(2019, 10, 1));
        transaction3.setDescription("mijn loon");
        transaction3.setRecurring(Recurring.EVERY_MONTH);
        transaction3.setCostPost(costPost3);
        transaction3.setValidated(true);
        transactionService.save(transaction3);

        System.out.println("initial data is loaded");
    }
}
