package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.TransactionCommand;
import be.annelyse.budget.model.Transaction;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TransactionToTransactionCommand implements Converter<Transaction, TransactionCommand> {

    private final TagToTagCommand tagConverter;
    private final CategoryToCategoryCommand categoryConverter;
    private final CostPostToCostPostCommand costPostConverter;
    private final AccountToAccountCommand accountConverter;

    public TransactionToTransactionCommand(TagToTagCommand tagConverter, CategoryToCategoryCommand categoryConverter, CostPostToCostPostCommand costPostConverter, AccountToAccountCommand accountConverter) {
        this.tagConverter = tagConverter;
        this.categoryConverter = categoryConverter;
        this.costPostConverter = costPostConverter;
        this.accountConverter = accountConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public TransactionCommand convert(Transaction source) {
        if (source == null){
            return null;
        }

        final TransactionCommand result = new TransactionCommand();
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
        result.setFlow(source.getFlow());
        result.setAccount(accountConverter.convert(source.getAccount()));

        if(source.getTags() != null && source.getTags().size() > 0){
            source.getTags()
                    .forEach(tag -> result.getTags().add(tagConverter.convert(tag)));
        }

        return result;
    }
}
