package pl.micede.personalapi.utils.mapper;

import lombok.*;
import org.springframework.stereotype.Component;
import pl.micede.personalapi.dto.TargetReadDto;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.TargetModel;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class TargetMapper {

    /**
     * Converts a TargetModel entity to a TargetReadDto
     *
     * @param model the targetModel entity to convert
     * @return The converted TargetReadDto
     */

    public TargetReadDto toDto(TargetModel model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startingDateFormatted = model.getTargetBegins().format(formatter);
        String endingDateFormatted = model.getTargetEnds().format(formatter);
        return TargetReadDto.builder()
                .id(model.getId())
                .targetName(model.getTargetName())
                .description(model.getDescription())
                .targetBegins(startingDateFormatted)
                .targetEnds(endingDateFormatted)
                .targetCategory(model.getTargetCategory())
                .habits(model.getHabits())
                .build();
    }


}
