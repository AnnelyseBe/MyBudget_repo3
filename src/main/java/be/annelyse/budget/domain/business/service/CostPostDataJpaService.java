package be.annelyse.budget.domain.business.service;

import be.annelyse.budget.domain.business.model.CostPost;
import be.annelyse.budget.domain.dao.CostPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
