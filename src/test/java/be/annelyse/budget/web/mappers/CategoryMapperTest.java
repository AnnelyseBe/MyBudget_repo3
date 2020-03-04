package be.annelyse.budget.web.mappers;

import be.annelyse.budget.domain.business.model.Category;
import be.annelyse.budget.web.dto.CategoryDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    public static final String NAME = "name";
    public static final long ID = 1L;
    public static final String DESCRIPTION = "description";

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDto() {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);
        category.setDescription(DESCRIPTION);

        //when
        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(category);

        //then
        assertEquals(Long.valueOf(ID), categoryDto.getId());
        assertEquals(NAME, categoryDto.getName());
        assertEquals(DESCRIPTION, categoryDto.getDescription());

    }

}