package be.annelyse.budget.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto{

    //todo evalueren of de transactions terug moeten worden toegevoegd

    private Long id;
    private String name;
    private String number;
    private String description;
    private Boolean active = true;
    private Currency currency;
}
