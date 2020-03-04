package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.AccountDto;
import be.annelyse.budget.domain.business.model.Account;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AccountToAccountDto implements Converter<Account, AccountDto> {

    //deze annotaties hebben te maken met threadsafety
    @Synchronized
    @Nullable
    @Override
    public AccountDto convert(Account source) {
        if (source == null){
            return null;
        }

        //final voor threadsafety
        final AccountDto result = new AccountDto();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setNumber(source.getNumber());
        result.setCurrency(source.getCurrency());
        result.setDescription(source.getDescription());
        result.setActive(source.getActive());

        return result;
    }
}
