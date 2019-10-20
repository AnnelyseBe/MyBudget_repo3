package be.annelyse.budget.service.map;

import be.annelyse.budget.model.Transaction;
import be.annelyse.budget.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Profile({"default", "map"})
public class TransactionMapService extends AbstractMapService<Transaction, Long> implements TransactionService {
    @Override
    public Set<Transaction> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Transaction transaction) {
        super.delete(transaction);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return super.save(transaction);
    }

    @Override
    public Transaction findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Set<Transaction> findTransactionByDescriptionContaining(String description) {
        return this.findAll()
                .stream()
                .filter(transaction -> transaction.getDescription().toLowerCase().contains(description.toLowerCase()))
                .collect(Collectors.toSet());
    }
}
