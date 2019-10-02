package be.annelyse.budget.repositories;

import be.annelyse.budget.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoriesByNameContains(String name);
}
