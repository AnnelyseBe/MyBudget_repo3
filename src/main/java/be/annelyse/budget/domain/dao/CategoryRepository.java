package be.annelyse.budget.domain.dao;

import be.annelyse.budget.domain.business.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoriesByNameContains(String name);
}
