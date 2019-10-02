package be.annelyse.budget.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"costPosts"}, callSuper = true)
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<CostPost> costPosts = new HashSet<>();

    @Builder
    public Category(Long id, String name, String description, Set<CostPost> costPosts) {
        super(id);
        this.name = name;
        this.description = description;
        this.costPosts = costPosts;
    }

    public void addCostPost(CostPost costPost) {
        this.getCostPosts().add(costPost);
    }
}
