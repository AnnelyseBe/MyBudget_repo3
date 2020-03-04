package be.annelyse.budget.domain.dao;

import be.annelyse.budget.domain.business.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
