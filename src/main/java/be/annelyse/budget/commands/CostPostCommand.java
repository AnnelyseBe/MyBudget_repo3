package be.annelyse.budget.commands;

import be.annelyse.budget.model.Category;
import be.annelyse.budget.model.FlowType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class CostPostCommand {

    private Long id;
    private String name;
    private String description;
    private FlowType flow;
    private CategoryCommand category;
    private Boolean costPostActive = true;
}
