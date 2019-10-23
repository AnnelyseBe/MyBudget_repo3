package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.model.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountToAccountCommand implements Converter<Account, AccountCommand> {

    @Override
    public AccountCommand convert(Account source) {
        if (source == null){
            return null;
        }


    }
}
