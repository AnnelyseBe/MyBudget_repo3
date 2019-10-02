package be.annelyse.budget.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"transactions"}, callSuper = true)
@Entity
@Table(name = "costpost")
public class CostPost extends BaseEntity {

    @Column(name = "name")
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
        super(id);
        this.name = name;
        this.description = description;
        this.flow = flow;
        this.category = category;
        this.costPostActive = costPostActive;
        this.transactions = transactions;
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
