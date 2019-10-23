package be.annelyse.budget.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"costPosts"})
@Entity
@Table(name = "category")
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<CostPost> costPosts = new HashSet<>();

    @Builder
    public Category(Long id, String name, String description, Set<CostPost> costPosts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.costPosts = costPosts;
    }

    public void addCostPost(CostPost costPost) {
        this.getCostPosts().add(costPost);
    }
}
