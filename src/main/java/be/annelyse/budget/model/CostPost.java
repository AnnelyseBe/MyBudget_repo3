package be.annelyse.budget.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"transactions"})
@Entity
@Table(name = "costpost")
public class CostPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Size(min = 3, max = 255)
    @NotBlank
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "flowtype")
    @Enumerated(EnumType.STRING)
    private FlowType flow;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "active")
    private Boolean costPostActive = true;

    @OneToMany(mappedBy = "costPost")
    private Set<Transaction> transactions = new HashSet<>();

    @Builder
    public CostPost(Long id, String name, String description, FlowType flow, Category category, Boolean costPostActive, Set<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.flow = flow;
        this.category = category;
        this.costPostActive = costPostActive;
        if (transactions != null){
            this.transactions = transactions;
        }
    }

    public CostPost setCategory(Category category) {
        this.category = category;
        category.addCostPost(this);
        return this;
    }

    public void addTransaction(Transaction transaction) {
        this.getTransactions().add(transaction);
    }
}
