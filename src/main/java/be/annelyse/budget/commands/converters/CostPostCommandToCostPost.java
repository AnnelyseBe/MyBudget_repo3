package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.CostPostCommand;
import be.annelyse.budget.model.CostPost;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CostPostCommandToCostPost implements Converter<CostPostCommand, CostPost> {

    private final CategoryCommandToCategory categoryConverter;

    public CostPostCommandToCostPost(CategoryCommandToCategory categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public CostPost convert(CostPostCommand source){

        if (source == null){
            return null;
        }

        final CostPost result = new CostPost();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());
        result.setFlow(source.getFlow());
        result.setCostPostActive(source.getCostPostActive());
        result.setCategory(categoryConverter.convert(source.getCategory()));

        return result;
    }
}
