package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.model.Account;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AccountToAccountCommand implements Converter<Account, AccountCommand> {

    //deze annotaties hebben te maken met threadsafety
    @Synchronized
    @Nullable
    @Override
    public AccountCommand convert(Account source) {
        if (source == null){
            return null;
        }

        //final voor threadsafety
        final AccountCommand result = new AccountCommand();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setNumber(source.getNumber());
        result.setCurrency(source.getCurrency());
        result.setDescription(source.getDescription());
        result.setActive(source.getActive());

        return result;
    }
}
