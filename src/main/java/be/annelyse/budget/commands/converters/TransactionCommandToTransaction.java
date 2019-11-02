package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.CostPostCommand;
import be.annelyse.budget.commands.TagCommand;
import be.annelyse.budget.commands.TransactionCommand;
import be.annelyse.budget.model.Recurring;
import be.annelyse.budget.model.Transaction;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class TransactionCommandToTransaction implements Converter<TransactionCommand, Transaction> {

    private final TagCommandToTag tagConverter;
    private final CategoryCommandToCategory categoryConverter;
    private final CostPostCommandToCostPost costPostConverter;
    private final AccountCommandToAccount accountConverter;

    public TransactionCommandToTransaction(TagCommandToTag tagConverter, CategoryCommandToCategory categoryConverter, CostPostCommandToCostPost costPostConverter, AccountCommandToAccount accountConverter) {
        this.tagConverter = tagConverter;
        this.categoryConverter = categoryConverter;
        this.costPostConverter = costPostConverter;
        this.accountConverter = accountConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Transaction convert(TransactionCommand source) {
        if (source == null){
            return null;
        }

        final Transaction result = new Transaction();
        result.setId(source.getId());
        result.setCostPost(costPostConverter.convert(source.getCostPost()));
        result.setNotes(source.getNotes());
        result.setExtra(source.getExtra());
        result.setDate(source.getDate());
        result.setDescription(source.getDescription());
        result.setRecurring(source.getRecurring());
        result.setValidated(source.getValidated());
        result.setOutflow(source.getOutflow());
        result.setInflow(source.getInflow());
        result.setAccount(accountConverter.convert(source.getAccount()));

        if(source.getTags() != null && source.getTags().size() > 0){
            source.getTags()
                    .forEach(tag -> result.getTags().add(tagConverter.convert(tag)));
        }

        return result;
    }
}
