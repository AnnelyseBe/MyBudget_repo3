package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.CategoryDto;
import be.annelyse.budget.domain.business.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoToCategory implements Converter<CategoryDto, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryDto source){

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
