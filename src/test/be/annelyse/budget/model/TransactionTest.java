package be.annelyse.budget.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class TransactionTest {

    private Transaction transaction = new Transaction();

    @Test
    void setFlow() {
        transaction.setInflow(null);
        transaction.setOutflow(null);
        assertThat(transaction.getFlow(), equalTo(new BigDecimal("0")));

        transaction.setInflow(new BigDecimal("100"));
        transaction.setOutflow(new BigDecimal("500.0"));
        assertThat(transaction.getFlow(), equalTo(BigDecimal.valueOf(-400D)));

        transaction.setInflow(null);
        transaction.setOutflow(BigDecimal.valueOf(500));
        assertThat(transaction.getFlow(), equalTo(BigDecimal.valueOf(-500)));

        transaction.setInflow(BigDecimal.valueOf(500));
        transaction.setOutflow(null);
        assertThat(transaction.getFlow(), equalTo(BigDecimal.valueOf(500)));

        transaction.setInflow(BigDecimal.valueOf(500.8D));
        transaction.setOutflow(BigDecimal.valueOf(200.8D));
        assertThat(transaction.getFlow(), equalTo(BigDecimal.valueOf(300.0D)));
        assertThat(transaction.getFlow(), equalTo(BigDecimal.valueOf(300D)));
    }
}