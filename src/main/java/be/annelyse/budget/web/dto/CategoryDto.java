package be.annelyse.budget.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    //todo evalueren of de costposts niet terug moeten worden toegevoegd

    private Long id;
    private String name;
    private String description;

}
