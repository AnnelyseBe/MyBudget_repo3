package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.TagCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Tag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagCommandToTag  implements Converter<TagCommand, Tag> {

    TransactionCommandToTransaction transactionConverter;


    @Override
    public Tag convert(TagCommand source) {
        if (source == null){
            return null;
        }

        final Tag tag = new Tag();
        tag.setName(source.getName());
        tag.setId(source.getId());

        if (source.getTransactions() != null && source.getTransactions().size() > 0){
            source.getTransactions()
                    .forEach(transaction -> tag.getTransactions().add(transactionConverter.convert(transaction)));
        }

        return tag;
    }
}
