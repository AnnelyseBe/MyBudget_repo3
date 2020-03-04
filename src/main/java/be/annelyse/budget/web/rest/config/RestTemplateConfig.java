package be.annelyse.budget.web.rest.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    //Om een http-call te doen vanuit je programma naar een Http-endpoint (van een andere service)
    //dit kan dan in een service met restTemplate.getForObject
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

}
