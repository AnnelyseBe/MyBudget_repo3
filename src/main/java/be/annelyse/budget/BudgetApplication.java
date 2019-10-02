package be.annelyse.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @Configuration (beans definiëren)
// @ComponentScan (zoekt beans in pakket en subpakketten waar deze file in zit)
// @EnableAutoConfiguration geldt voor het pakket en subpakketten waar deze file in zit
public class BudgetApplication {

    public static void main(String[] args) {
        //configuratie van de applicatie (context)
        SpringApplication.run(BudgetApplication.class, args);
    }

}
