package pl.micede.personalapi.utils.mapper;

import org.springframework.stereotype.Component;
import pl.micede.personalapi.dto.TargetReadDto;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.TargetModel;

import java.time.format.DateTimeFormatter;

@Component
public class TargetMapper {

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

    public TargetModel toModel(TargetReqDto reqDto) {
        return TargetModel.builder()
                .targetName(reqDto.getTargetName())
                .description(reqDto.getDescription())
                .targetCategory(reqDto.getTargetCategory())
                .targetBegins(reqDto.getTargetBegins())
                .targetEnds(reqDto.getTargetEnds())
                .habits(reqDto.getHabits())
                .build();
    }

    public TargetReqDto toReqDto(TargetModel targetModel) {
        return TargetReqDto.builder()
                .targetName(targetModel.getTargetName())
                .description(targetModel.getDescription())
                .targetCategory(targetModel.getTargetCategory())
                .targetBegins(targetModel.getTargetBegins())
                .targetEnds(targetModel.getTargetEnds())
                .habits(targetModel.getHabits())
                .build();
    }


}
