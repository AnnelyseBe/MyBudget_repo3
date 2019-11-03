package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.model.Category;
import be.annelyse.budget.repositories.CategoryRepository;
import be.annelyse.budget.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile("springdatajpa")
public class CategoryDataJpaService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDataJpaService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
