package be.annelyse.budget.domain.business.service;

import be.annelyse.budget.domain.business.model.Tag;
import be.annelyse.budget.domain.dao.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
public class TagDataJpaService implements TagService {

    private final TagRepository tagRepository;

    public TagDataJpaService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList<>();
        tagRepository.findAll().forEach(tags::add);
        return tags;
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
