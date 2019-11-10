package be.annelyse.budget.controllers;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.exceptions.NotFoundException;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

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
    private static final String VIEW_400_ERROR = "errors/400Error";
    private static final String VIEW_404_ERROR = "errors/404Error";

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //zorgt ervoor dat alles buiten het ID door de Bindingresult wordt gekoppeld. De id moeten we dan zelf binden
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
        model.addAttribute("currenciesToChoose", accountService.getCurrenciesToChoose());
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
    public String processCreationForm(@Valid Account account, BindingResult result, Model model){
        log.debug("AccountController newAccount() reached");
        model.addAttribute("account", account);
        model.addAttribute("currenciesToChoose", accountService.getCurrenciesToChoose());

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
    public String processUpdateAccountForm(@Valid Account account, BindingResult result, @PathVariable Long id, Model model){
        log.debug("AccountController processUpdateAccountForm reached: id " + account.getId() );
        model.addAttribute("account", account);
        model.addAttribute("currenciesToChoose", accountService.getCurrenciesToChoose());
        if(result.hasErrors()){
            return VIEWS_ACCOUNT_CREATE_OR_UPDATE_FORM;
        } else{
            account.setId(id); //moet wegens de initbinder
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(Exception exception, Model model){

        log.debug("AccountController - handleNotFound has been reached");
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        model.addAttribute("exception", exception);

        return VIEW_404_ERROR;
    }


    // dit werkt nog niet
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public String handleBadRequest(Exception exception, Model model){

        log.debug("AccountController - handleBadRequest Reached");
        log.error("Bad Request");
        log.error(exception.getMessage());
        model.addAttribute("exception", exception);

        return VIEW_400_ERROR;
    }
}
