package be.annelyse.budget.service.map;

import be.annelyse.budget.model.CostPost;
import be.annelyse.budget.service.CostPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile({"default", "map"})
public class CostPostMapService extends AbstractMapService<CostPost, Long> implements CostPostService {
    @Override
    public Set<CostPost> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(CostPost costPost) {
        super.delete(costPost);

    }

    @Override
    public CostPost save(CostPost costPost) {
        return super.save(costPost);
    }

    @Override
    public CostPost findById(Long id) {
        return super.findById(id);
    }
}
