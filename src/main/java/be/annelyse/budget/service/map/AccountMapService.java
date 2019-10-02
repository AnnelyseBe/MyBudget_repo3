package be.annelyse.budget.service.map;

import be.annelyse.budget.model.Account;
import be.annelyse.budget.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile({"default", "map"})
public class AccountMapService extends AbstractMapService<Account, Long> implements AccountService {
    @Override
    public Set<Account> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Account account) {
        super.delete(account);
    }

    @Override
    public Account save(Account account) {
        return super.save(account);
    }

    @Override
    public Account findById(Long id) {
        return super.findById(id);
    }
}
