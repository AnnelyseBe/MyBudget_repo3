package be.annelyse.budget.controllers;

import be.annelyse.budget.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@RequestMapping("/accounts")
@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping({"/", "", "/index", "/index.html"})
    public String listAccounts(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "accounts/index";
    }

    @RequestMapping("/find")
    public String findAccounts() {
        return "notimplemented";
    }




}
