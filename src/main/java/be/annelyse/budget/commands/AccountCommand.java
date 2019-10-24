package be.annelyse.budget.commands;

import be.annelyse.budget.model.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccountCommand{

    //todo evalueren of de transactions terug moeten worden toegevoegd

    private Long id;
    private String name;
    private String number;
    private String description;
    private Boolean active = true;
    private Currency currency;
}
