package be.annelyse.budget.gateway;

import be.annelyse.budget.web.dto.CategoryDto;

public interface ApiService {

    CategoryDto getCategory(Integer limit);
}
