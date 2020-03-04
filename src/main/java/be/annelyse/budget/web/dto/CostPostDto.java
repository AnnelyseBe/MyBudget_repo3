package be.annelyse.budget.web.dto;

import be.annelyse.budget.domain.business.model.FlowType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CostPostDto {

    private Long id;
    private String name;
    private String description;
    private FlowType flow;
    private CategoryDto category;
    private Boolean costPostActive = true;
}
