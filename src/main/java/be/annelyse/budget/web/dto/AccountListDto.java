package be.annelyse.budget.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountListDto {

    private final List<AccountDto> accounts;

}
