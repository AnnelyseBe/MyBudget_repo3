package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.CostPostDto;
import be.annelyse.budget.domain.business.model.CostPost;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CostPostDtoToCostPost implements Converter<CostPostDto, CostPost> {

    private final CategoryDtoToCategory categoryConverter;

    public CostPostDtoToCostPost(CategoryDtoToCategory categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public CostPost convert(CostPostDto source){

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
