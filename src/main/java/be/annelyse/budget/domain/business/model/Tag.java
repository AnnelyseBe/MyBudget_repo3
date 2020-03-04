package be.annelyse.budget.domain.business.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
//todo .. ik wil hier precies nog een description bij

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
    @Size(min = 3, max = 255)
    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Transaction> transactions = new HashSet<>();

    @Builder
    public Tag(Long id, String name, Set<Transaction> transactions) {

        this.id = id;
        this.name = name;
        if (transactions != null){
            this.transactions = transactions;
        }
    }

    public void addTransaction(Transaction transaction) {
        this.getTransactions().add(transaction);
    }

}

