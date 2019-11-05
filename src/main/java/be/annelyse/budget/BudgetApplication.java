package be.annelyse.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
// @Configuration (beans definiëren)
// @ComponentScan (zoekt beans in pakket en subpakketten waar deze file in zit)
// @EnableAutoConfiguration geldt voor het pakket en subpakketten waar deze file in zit
public class BudgetApplication {

    public static void main(String[] args) {
        //configuratie van de applicatie (context)
        SpringApplication.run(BudgetApplication.class, args);
    }

    //todo diy is een test vanuit thymeleaf voorbeeld. komt er een internationalisation message ding bij resoures???
    /*
     *  Message externalization/internationalization
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("Messages");
        return messageSource;
    }

}
