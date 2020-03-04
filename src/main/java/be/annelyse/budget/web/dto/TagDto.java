package be.annelyse.budget.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TagDto {

    //todo evalueren of de transactions niet terug moeten worden toegevoegd


    private Long id;
    private String name;

}
