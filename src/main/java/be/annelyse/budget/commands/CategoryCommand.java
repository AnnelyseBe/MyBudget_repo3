package be.annelyse.budget.commands;

import be.annelyse.budget.model.CostPost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {

    //todo evalueren of de costposts niet terug moeten worden toegevoegd

    private Long id;
    private String name;
    private String description;

}
