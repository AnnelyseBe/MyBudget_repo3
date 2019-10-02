package be.annelyse.budget.repositories;

import be.annelyse.budget.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
