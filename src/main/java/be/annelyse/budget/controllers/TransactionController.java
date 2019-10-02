package be.annelyse.budget.controllers;

import be.annelyse.budget.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping({"/", "", "/index", "/index.html"})
    public String listTransactions(Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        return "transactions/index";
    }

    @RequestMapping({"/find"})
    public String findTransactions() {
        //todo implementation
        return "notimplemented";
    }
}
