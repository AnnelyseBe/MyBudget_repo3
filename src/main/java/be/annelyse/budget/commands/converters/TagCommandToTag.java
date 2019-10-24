package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.AccountCommand;
import be.annelyse.budget.commands.TagCommand;
import be.annelyse.budget.model.Account;
import be.annelyse.budget.model.Tag;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TagCommandToTag  implements Converter<TagCommand, Tag> {

    @Synchronized
    @Nullable
    @Override
    public Tag convert(TagCommand source) {
        if (source == null){
            return null;
        }

        final Tag result = new Tag();
        result.setName(source.getName());
        result.setId(source.getId());

        return result;
    }

}
