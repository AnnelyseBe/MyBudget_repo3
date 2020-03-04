package be.annelyse.budget.domain.dao;

import be.annelyse.budget.domain.business.model.CostPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostPostRepository extends JpaRepository<CostPost, Long> {
}
