package be.annelyse.budget.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

//let op ... als we iets veranderen aan het domein, moeten we ook de commands aanpassen

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"transactions"})
@Entity //JPA-hibernate geeft aan dat dit een model is
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private String number;

    @Lob  //todo ... is Lob hier de goede annotatie voor?
    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "currency")
    private Currency currency = Currency.getInstance("EUR");

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

    //todo use transient of not @Transient or just dont use in the domainobject
/*    private BigDecimal balance;*/

    @Builder
    public Account(Long id, String name, String number, String description, Boolean active, Currency currency, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.description = description;
        this.active = active;
        this.currency = currency;
        if (transactions != null){
            this.transactions = transactions;
        }

    }

    public void addTransaction(Transaction transaction) {
        this.getTransactions().add(transaction);
    }
}
