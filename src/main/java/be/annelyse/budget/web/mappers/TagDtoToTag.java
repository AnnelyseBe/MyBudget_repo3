package be.annelyse.budget.web.mappers;

import be.annelyse.budget.web.dto.TagDto;
import be.annelyse.budget.domain.business.model.Tag;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TagDtoToTag implements Converter<TagDto, Tag> {

    @Synchronized
    @Nullable
    @Override
    public Tag convert(TagDto source) {
        if (source == null){
            return null;
        }

        final Tag result = new Tag();
        result.setName(source.getName());
        result.setId(source.getId());

        return result;
    }

}
