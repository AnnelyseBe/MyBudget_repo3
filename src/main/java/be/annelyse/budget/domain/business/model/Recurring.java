package be.annelyse.budget.domain.business.model;

import java.io.Serializable;

//todo ... kan dit ev. met een interface???
public enum Recurring implements Serializable {
    NONE, EVERY_MONTH, EVERY_3_MONTHS, EVERY_YEAR;
}
