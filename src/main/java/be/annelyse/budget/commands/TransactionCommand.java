package be.annelyse.budget.commands;

import be.annelyse.budget.model.Recurring;
import be.annelyse.budget.model.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public TransactionCommand setInflow(BigDecimal inflow) {
        this.inflow = inflow;
        this.setFlow();
        return this;
    }

    public TransactionCommand setOutflow(BigDecimal outflow) {
        this.outflow = outflow;
        this.setFlow();
        return this;
    }

    //todo dit is absoluut geen clean code
    private TransactionCommand setFlow() {
        if (this.inflow == null && this.outflow == null) {
            this.flow = new BigDecimal(0);
        } else if (this.inflow != null && this.outflow == null) {
            this.flow = inflow;
        } else if (this.inflow == null) {
            this.flow = new BigDecimal(0).subtract(this.outflow);
        } else {
            this.flow = this.inflow.subtract(this.outflow);
        }
        return this;
    }
}
