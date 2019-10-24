package be.annelyse.budget.commands;

import be.annelyse.budget.model.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TagCommand {

    //todo evalueren of de transactions niet terug moeten worden toegevoegd


    private Long id;
    private String name;

}
