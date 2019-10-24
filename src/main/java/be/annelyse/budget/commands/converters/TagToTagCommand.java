package be.annelyse.budget.commands.converters;

import be.annelyse.budget.commands.TagCommand;
import be.annelyse.budget.model.Tag;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TagToTagCommand implements Converter<Tag, TagCommand> {

    @Synchronized
    @Nullable
    @Override
    public TagCommand convert(Tag source) {
        if (source == null){
            return null;
        }

        final TagCommand result = new TagCommand();
        result.setName(source.getName());
        result.setId(source.getId());

        return result;
    }

}
