package be.annelyse.budget.repositories;

import be.annelyse.budget.model.CostPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostPostRepository extends JpaRepository<CostPost, Long> {
}
