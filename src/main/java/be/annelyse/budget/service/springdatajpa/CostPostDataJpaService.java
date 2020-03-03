package be.annelyse.budget.service.springdatajpa;

import be.annelyse.budget.model.CostPost;
import be.annelyse.budget.repositories.CostPostRepository;
import be.annelyse.budget.service.CostPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
public class CostPostDataJpaService implements CostPostService {

    private final CostPostRepository costPostRepository;

    public CostPostDataJpaService(CostPostRepository costPostRepository) {
        this.costPostRepository = costPostRepository;
    }

    @Override
    public List<CostPost> findAll() {
        List<CostPost> costPosts = new ArrayList<>();
        costPostRepository.findAll().forEach(costPosts::add);
        return costPosts;
    }

    @Override
    public CostPost findById(Long id) {
        return costPostRepository.findById(id).orElse(null);
    }

    @Override
    public CostPost save(CostPost costPost) {
        return costPostRepository.save(costPost);
    }

    @Override
    public void delete(CostPost costPost) {
        costPostRepository.delete(costPost);
    }

    @Override
    public void deleteById(Long id) {
        costPostRepository.deleteById(id);
    }
}
