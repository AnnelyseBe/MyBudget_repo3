package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.AccountDto;
import be.annelyse.budget.domain.business.model.Account;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class
AccountDtoToAccount implements Converter<AccountDto, Account> {

    @Synchronized
    @Nullable
    @Override
    public Account convert(AccountDto source){

        if (source == null){
            return null;
        }

        final Account result = new Account();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setNumber(source.getNumber());
        result.setActive(source.getActive());
        result.setCurrency(source.getCurrency());
        result.setDescription(source.getDescription());

        return result;
    }
}
