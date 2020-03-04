package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.TagDto;
import be.annelyse.budget.domain.business.model.Tag;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TagToTagDto implements Converter<Tag, TagDto> {

    @Synchronized
    @Nullable
    @Override
    public TagDto convert(Tag source) {
        if (source == null){
            return null;
        }

        final TagDto result = new TagDto();
        result.setName(source.getName());
        result.setId(source.getId());

        return result;
    }

}
