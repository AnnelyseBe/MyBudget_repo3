package be.annelyse.budget.service.map;

import be.annelyse.budget.model.Tag;
import be.annelyse.budget.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile({"default", "map"})
public class TagMapService extends AbstractMapService<Tag, Long> implements TagService {
    @Override
    public Set<Tag> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);

    }

    @Override
    public void delete(Tag tag) {
        super.delete(tag);
    }

    @Override
    public Tag save(Tag tag) {
        return super.save(tag);
    }

    @Override
    public Tag findById(Long id) {
        return super.findById(id);
    }
}
