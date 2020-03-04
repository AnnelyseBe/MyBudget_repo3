package be.annelyse.budget.gateway;

import be.annelyse.budget.web.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ApiServiceImpl implements ApiService {

    private RestTemplate restTemplate;

    private final String api_url;

/*spring expression language -> property uit application.properties gebruiken en injecteren at runtime*/
    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String api_url) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }


    @Override
    public CategoryDto getCategory(Integer limit) {

        //URL opbouwen ... mooie manier
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(api_url)
                .queryParam("limit", limit);

        CategoryDto category = restTemplate.getForObject(uriBuilder.toUriString(), CategoryDto.class);
        return category;
    }
}
