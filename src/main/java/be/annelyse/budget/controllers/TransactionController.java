package be.annelyse.budget.controllers;


import be.annelyse.budget.service.AccountService;
import be.annelyse.budget.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;


    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;

    }

    @GetMapping({"/", "", "/index", "/index.html"})
    public String listTransactions(Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        return "transactions/index";
    }

    @GetMapping("/find")
    public String findTransactions(Model model) {
        //todo implement
        return "notimplemented";
    }

    @GetMapping("/account/{accountId}")
    public String listTransactionsByAccountId(@PathVariable String accountId, Model model) {
        log.debug("Getting transaction list for account id: " + accountId);
        //use command object to avoid lazy load erros in Thymeleaf
        model.addAttribute("transactions", transactionService.findCommandsByAccountId(Long.valueOf(accountId)));
        return "transactions/list";
    }


}
