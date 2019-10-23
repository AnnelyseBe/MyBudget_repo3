package be.annelyse.budget.model;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"transactions"})
@Entity
@Table(name = "tag")
public class Tag{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Transaction> transactions = new HashSet<>();

    @Builder
    public Tag(Long id, String name, Set<Transaction> transactions) {

        this.id = id;
        this.name = name;
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.getTransactions().add(transaction);
    }

}

