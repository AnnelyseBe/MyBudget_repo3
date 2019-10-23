package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.TransactionCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionCommandToTransaction implements Converter<TransactionCommand, Transaction> {

    TagCommandToTag tagConverter;




    @Override
    public Transaction convert(TransactionCommand source) {
        if (source == null){
            return null;
        }

        final Transaction transaction = new Transaction();
        transaction.setId(source.getId());
        transaction.setCostPost(source.getCostPost());
        transaction.setDate(source.getDate());
        transaction.setDescription(source.getDescription());
        transaction.setRecurring(source.getRecurring());
        transaction.setValidated(source.getValidated());
        transaction.setOutflow(source.getOutflow());
        transaction.setInflow(source.getInflow());
        transaction.setAccount(source.getAccount());

        if(source.getTags() != null && source.getTags().size() > 0){
            source.getTags()
                    .forEach(tag -> transaction.getTags().add(tagConverter.convert(tag)));
        }

        return transaction;


    }
}
