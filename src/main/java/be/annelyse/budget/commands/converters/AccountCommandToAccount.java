package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.model.Account;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AccountCommandToAccount implements Converter<AccountCommand, Account> {

    private final TransactionCommandToTransaction transactionConverter;



    @Synchronized
    @Nullable
    @Override
    public Account convert(AccountCommand source){
        if (source == null){
            return null;
        }

        final Account account = new Account();
        account.setId(source.getId());
        account.setName(source.getName());
        account.setNumber(source.getNumber());
        account.setActive(source.getActive());
        account.setCurrency(source.getCurrency());
        account.setDescription(source.getDescription());

        if(source.getTransactions() != null && source.getTransactions().size() > 0){
            source.getTransactions()
                    .forEach(transaction -> account.getTransactions().add(transactionConverter.convert(transaction)));
        }

        return account;




    }
}
