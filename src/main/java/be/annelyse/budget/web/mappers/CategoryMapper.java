package be.annelyse.budget.web.mappers;

import be.annelyse.budget.domain.business.model.Category;
import be.annelyse.budget.web.dto.CategoryDto;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

//zie mapstruct, ik ben niet zeker of ik hier een fan van ben
@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = getMapper(CategoryMapper.class);

    CategoryDto categoryToCategoryDto(Category category);
}
