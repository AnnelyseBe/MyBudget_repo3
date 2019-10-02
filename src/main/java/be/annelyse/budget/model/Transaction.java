package be.annelyse.budget.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "inflow")
    private BigDecimal inflow;

    @Column(name = "outflow")
    private BigDecimal outflow;

    @Transient
    private BigDecimal flow;

    @Column(name = "description")
    private String description;

    @Column(name = "notes")
    private String notes;

    @Column(name = "extra")
    private String extra;

    @ManyToOne
    @JoinColumn(name = "costpost_id")
    private CostPost costPost;

    @Column(name = "validated")
    private Boolean validated = false;

    @Enumerated(EnumType.STRING)
    private Recurring recurring = Recurring.NONE;

    @ManyToMany
    @JoinTable(name = "transaction_tags",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Builder
    public Transaction(Long id, Account account, LocalDate date, BigDecimal inflow, BigDecimal outflow, String description, String notes, String extra, CostPost costPost, Boolean validated, Recurring recurring, Set<Tag> tags) {
        super(id);
        this.account = account;
        this.date = date;
        this.inflow = inflow;
        this.outflow = outflow;
        this.description = description;
        this.notes = notes;
        this.extra = extra;
        this.costPost = costPost;
        this.validated = validated;
        this.recurring = recurring;
        this.tags = tags;
    }

    public Transaction setInflow(BigDecimal inflow) {
        this.inflow = inflow;
        this.setFlow();
        return this;
    }

    public Transaction setOutflow(BigDecimal outflow) {
        this.outflow = outflow;
        this.setFlow();
        return this;
    }

    //todo dit is absoluut geen clean code
    private Transaction setFlow() {
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

    public Transaction setAccount(Account account) {
        this.account = account;
        account.addTransaction(this);
        return this;
    }

    public Transaction setCostPost(CostPost costPost) {
        this.costPost = costPost;
        costPost.addTransaction(this);
        return this;
    }

    public Transaction addTag(Tag tag) {
        tag.addTransaction(this);
        this.tags.add(tag);
        return this;
    }
}

