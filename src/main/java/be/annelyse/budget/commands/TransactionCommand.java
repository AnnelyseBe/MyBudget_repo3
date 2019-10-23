package be.annelyse.budget.commands;

import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.CostPost;
import be.annelyse.budget.model.Recurring;
import be.annelyse.budget.model.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TransactionCommand {

    private Long id;
    private AccountCommand account;
    private LocalDate date;
    private BigDecimal inflow;
    private BigDecimal outflow;
    private BigDecimal flow;
    private String description;
    private String notes;
    private String extra;
    private CostPostCommand costPost;
    private Boolean validated = false;
    private Recurring recurring = Recurring.NONE;
    private Set<TagCommand> tags = new HashSet<>();
}
