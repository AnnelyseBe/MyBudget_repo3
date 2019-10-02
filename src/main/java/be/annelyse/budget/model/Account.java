package be.annelyse.budget.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"transactions"}, callSuper = true)
@Entity //JPA-hibernate geeft aan dat dit een model is
@Table(name = "account")
public class Account extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private String number;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "currency")
    private Currency currency;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<>();

    @Transient
    private BigDecimal balance;


    @Builder
    public Account(Long id, String name, String number, String description, Boolean active, Currency currency, Set<Transaction> transactions) {
        super(id);
        this.name = name;
        this.number = number;
        this.description = description;
        this.active = active;
        this.currency = currency;
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.getTransactions().add(transaction);
    }
}
