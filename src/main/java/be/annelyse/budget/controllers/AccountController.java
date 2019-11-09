package be.annelyse.budget.controllers;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@RequestMapping("/accounts")
@Controller
public class AccountController {

    private static final String VIEWS_ACCOUNT_DETAILS = "accounts/accountDetails";
    private static final String VIEWS_ACCOUNT_LIST = "accounts/accountsList";
    private static final String VIEWS_ACCOUNT_CREATE_OR_UPDATE_FORM = "accounts/createOrUpdateAccountForm";
    private static final String VIEWS_ACCOUNT_FIND = "accounts/findAccounts";

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findAccounts(Model model) {
        log.debug("AccountController findAccounts reached");
        model.addAttribute("account", Account.builder().build());
        return VIEWS_ACCOUNT_FIND;
    }

    @GetMapping()
    public String processFindForm(Account account, BindingResult result, Model model){
        //allow parameterless GET request for /accounts to return all records
        if (account.getName() == null){
            account.setName(""); //empty string signifies broadest possible search
        }

        //find accounts by name like
        List<Account> results = accountService.findAllByNameLike("%"+account.getName()+"%");

        if(results.isEmpty()){
            //no accounts found
            result.rejectValue("name","notFound", "not found");
            return VIEWS_ACCOUNT_FIND;
        } else if (results.size() == 1){
            // 1 account found
            account = results.get(0);
            return "redirect:/accounts/" + account.getId();
        } else {
            // multiple accounts found
            model.addAttribute("accounts", results);
            return VIEWS_ACCOUNT_LIST ;
        }
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable Long id, Model model){
        log.debug("AccountController showById reached: id " + id);
        model.addAttribute("account", accountService.findById(id));
        return VIEWS_ACCOUNT_DETAILS;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model){
        log.debug("AccountController initCreationForm() reached");
        model.addAttribute("account", Account.builder().build());
        model.addAttribute("currenciesInUse", accountService.getCurrenciesToChoose());
        return VIEWS_ACCOUNT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Account account, BindingResult result){
        log.debug("AccountController newAccount() reached");
        if (result.hasErrors()){
            return VIEWS_ACCOUNT_CREATE_OR_UPDATE_FORM;
        } else {
            Account savedAccount = accountService.save(account);
            return "redirect:/accounts/"+savedAccount.getId();
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateAccountForm(@PathVariable Long id, Model model){
        log.debug("AccountController initUpdateAccountForm reached: id " + id);
        model.addAttribute("account", accountService.findById(id));
        model.addAttribute("currenciesToChoose", accountService.getCurrenciesToChoose());
        return VIEWS_ACCOUNT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateAccountForm(@Valid Account account, BindingResult result, @PathVariable Long id){
        log.debug("AccountController processUpdateAccountForm reached: id " + account.getId() );

        if(result.hasErrors()){
            return VIEWS_ACCOUNT_CREATE_OR_UPDATE_FORM;
        } else{
            account.setId(id);
            Account savedAccount = accountService.save(account);
            return "redirect:/accounts/" + savedAccount.getId();
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("AccountController deleteById reached: id " + id);
        try{
            accountService.deleteById(Long.valueOf(id));
        } catch (RuntimeException re) {
            log.warn(re.getMessage());
        }
        return "redirect:/accounts";
    }
}
