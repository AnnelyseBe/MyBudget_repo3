package be.annelyse.budget.domain.business.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
//@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "account_id")
    private Account account;

    //todo als we hier problemen mee krijgen, kijk eens in code petclinic bij visitcontroller databinder
//    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

//    @Column(name = "inflow")
    private BigDecimal inflow;

//    @Column(name = "outflow")
    private BigDecimal outflow;

//    @Column(name = "description")
    @Size(min = 3, max = 255)
    @NotBlank
    private String description;

//    @Column(name = "notes")
    private String notes;

//    @Column(name = "extra")
    private String extra;

    @ManyToOne
//    @JoinColumn(name = "costpost_id")
    private CostPost costPost;

//    @Column(name = "validated")
    private Boolean validated = false;

    @Enumerated(EnumType.STRING)
    private Recurring recurring = Recurring.NONE;

    @ManyToMany
//    @JoinTable(name = "transaction_tags",
//            joinColumns = @JoinColumn(name = "transaction_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Transaction(Long id, Account account, LocalDate date, BigDecimal inflow, BigDecimal outflow, String description, String notes, String extra, CostPost costPost, Boolean validated, Recurring recurring, List<Tag> tags) {
        this.id = id;
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
        if (tags != null){
            this.tags = tags;
        }
    }

    public Transaction setInflow(BigDecimal inflow) {
        this.inflow = inflow;
        return this;
    }

    public Transaction setOutflow(BigDecimal outflow) {
        this.outflow = outflow;
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

