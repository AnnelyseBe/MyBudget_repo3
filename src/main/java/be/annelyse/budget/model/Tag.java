package be.annelyse.budget.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"transactions"}, callSuper = true)
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Transaction> transactions = new HashSet<>();

    @Builder
    public Tag(Long id, String name, Set<Transaction> transactions) {
        super(id);
        this.name = name;
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.getTransactions().add(transaction);
    }

}

