package be.annelyse.budget.controllers;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@RequestMapping("/accounts")
@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/", "", "/index", "/index.html"})
    public String listAccounts(Model model) {
        log.debug("AccountController listAccounts reached");
        model.addAttribute("accounts", accountService.findAll());
        return "accounts/index";
    }

    @GetMapping("/find")
    public String findAccounts() {
        log.debug("AccountController findAccounts reached");
        return "notimplemented";
    }

    @GetMapping("/{id}")
    public String toShowId(@PathVariable String id){
        return "redirect:/accounts/{id}/show";
    }

    @GetMapping("/{id}/show")
    public String showById(@PathVariable String id, Model model){
        log.debug("AccountController showById reached: id " + id);
        model.addAttribute("account", accountService.findById(new Long(id)));
        return "accounts/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("AccountController deleteById reached: id " + id);
        accountService.deleteById(Long.valueOf(id));
        return "redirect:/accounts";
    }

    @GetMapping("/new")
    public String newAccount(Model model){
        log.debug("AccountController newAccount() reached");
        model.addAttribute("account", new AccountCommand());
        return "accounts/form";
    }

    @GetMapping("/{id}/update")
    public String updateAccount(@PathVariable String id, Model model){
        log.debug("AccountController updateAccount reached: id " + id);
        model.addAttribute("account", accountService.findCommandById(Long.valueOf(id)));
        return  "accounts/form";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(@ModelAttribute AccountCommand command){
        log.debug("AccountController saveOrUpdate reached: id " + command.getId() );
        AccountCommand savedCommand = accountService.saveCommand(command);
        return "redirect:/accounts/" + savedCommand.getId();
    }
}
