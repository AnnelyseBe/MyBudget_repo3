package be.annelyse.budget.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

//let op ... als we iets veranderen aan het domein, moeten we ook de commands aanpassen
// todo, ik denk dat het een betere praktijk is om de validators op de commands te zetten, misschien moeten we dit overnemen op de commands en hier weglaten

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
    @Size(min = 3, max = 255)
    @NotBlank
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
