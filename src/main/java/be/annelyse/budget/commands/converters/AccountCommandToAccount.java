package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.model.Account;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AccountCommandToAccount implements Converter<AccountCommand, Account> {

    @Synchronized
    @Nullable
    @Override
    public Account convert(AccountCommand source){

        if (source == null){
            return null;
        }

        final Account result = new Account();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setNumber(source.getNumber());
        result.setActive(source.getActive());
        result.setCurrency(source.getCurrency());
        result.setDescription(source.getDescription());

        return result;
    }
}
