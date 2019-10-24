package be.annelyse.budget.controllers;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @RequestMapping("/{id}")
    public String showById(@PathVariable String id, Model model){
        model.addAttribute("account", accountService.findById(new Long(id)));
        return "accounts/showID";
    }

    @RequestMapping("/new")
    public String newAccount(Model model){
        model.addAttribute("account", new AccountCommand());
        return "accounts/form";
    }

    @RequestMapping("/{id}/update")
    public String updateAccount(@PathVariable String id, Model model){
        model.addAttribute("account", accountService.findCommandById(Long.valueOf(id)));
        return  "accounts/form";
    }

    //todo van deze ben ik nog niet zeker ivm de werking
    @PostMapping("")
    public String saveOrUpdate(@ModelAttribute AccountCommand command){
        AccountCommand savedCommand = accountService.saveCommand(command);
        return "redirect:/" + savedCommand.getId();
    }
}
