package be.annelyse.budget.service.map;

import be.annelyse.budget.model.Category;
import be.annelyse.budget.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile({"default", "map"})
public class CategoryMapService extends AbstractMapService<Category, Long> implements CategoryService {
    @Override
    public Set<Category> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Category category) {
        super.delete(category);
    }

    @Override
    public Category save(Category category) {
        return super.save(category);
    }

    @Override
    public Category findById(Long id) {
        return super.findById(id);
    }
}
