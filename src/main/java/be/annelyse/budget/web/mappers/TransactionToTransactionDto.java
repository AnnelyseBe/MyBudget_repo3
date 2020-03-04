package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.TransactionDto;
import be.annelyse.budget.domain.business.model.Transaction;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TransactionToTransactionDto implements Converter<Transaction, TransactionDto> {

    private final TagToTagDto tagConverter;
    private final CategoryToCategoryDto categoryConverter;
    private final CostPostToCostPostDto costPostConverter;
    private final AccountToAccountDto accountConverter;

    public TransactionToTransactionDto(TagToTagDto tagConverter, CategoryToCategoryDto categoryConverter, CostPostToCostPostDto costPostConverter, AccountToAccountDto accountConverter) {
        this.tagConverter = tagConverter;
        this.categoryConverter = categoryConverter;
        this.costPostConverter = costPostConverter;
        this.accountConverter = accountConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public TransactionDto convert(Transaction source) {
        if (source == null){
            return null;
        }

        final TransactionDto result = new TransactionDto();
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
