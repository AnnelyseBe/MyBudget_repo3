package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.CategoryCommand;
import be.annelyse.budget.commands.CostPostCommand;
import be.annelyse.budget.model.Category;
import be.annelyse.budget.model.CostPost;
import be.annelyse.budget.model.FlowType;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CostPostToCostPostCommand implements Converter<CostPost, CostPostCommand> {

    private final CategoryToCategoryCommand categoryConverter;

    public CostPostToCostPostCommand(CategoryToCategoryCommand categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public CostPostCommand convert(CostPost source) {
        if (source == null){
            return null;
        }

        final CostPostCommand result = new CostPostCommand();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());
        result.setFlow(source.getFlow());
        result.setCostPostActive(source.getCostPostActive());
        result.setCategory(categoryConverter.convert(source.getCategory()));

        return result;
    }
}
