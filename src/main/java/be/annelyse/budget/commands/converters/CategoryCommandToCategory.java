package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.CategoryCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source){

        if (source == null){
            return null;

        }

        final Category result = new Category();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());

        return result;
    }
}
