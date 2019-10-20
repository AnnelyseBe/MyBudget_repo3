package be.annelyse.budget.repositories;

import be.annelyse.budget.model.CostPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CostPostRepository extends CrudRepository<CostPost, Long> {
}
