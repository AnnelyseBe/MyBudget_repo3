package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.TransactionDto;
import be.annelyse.budget.domain.business.model.Transaction;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoToTransaction implements Converter<TransactionDto, Transaction> {

    private final TagDtoToTag tagConverter;
    private final CategoryDtoToCategory categoryConverter;
    private final CostPostDtoToCostPost costPostConverter;
    private final AccountDtoToAccount accountConverter;

    public TransactionDtoToTransaction(TagDtoToTag tagConverter, CategoryDtoToCategory categoryConverter, CostPostDtoToCostPost costPostConverter, AccountDtoToAccount accountConverter) {
        this.tagConverter = tagConverter;
        this.categoryConverter = categoryConverter;
        this.costPostConverter = costPostConverter;
        this.accountConverter = accountConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Transaction convert(TransactionDto source) {
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
