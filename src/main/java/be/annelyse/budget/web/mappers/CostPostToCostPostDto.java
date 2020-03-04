package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.CostPostDto;
import be.annelyse.budget.domain.business.model.CostPost;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CostPostToCostPostDto implements Converter<CostPost, CostPostDto> {

    private final CategoryToCategoryDto categoryConverter;

    public CostPostToCostPostDto(CategoryToCategoryDto categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public CostPostDto convert(CostPost source) {
        if (source == null){
            return null;
        }

        final CostPostDto result = new CostPostDto();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setDescription(source.getDescription());
        result.setFlow(source.getFlow());
        result.setCostPostActive(source.getCostPostActive());
        result.setCategory(categoryConverter.convert(source.getCategory()));

        return result;
    }
}
