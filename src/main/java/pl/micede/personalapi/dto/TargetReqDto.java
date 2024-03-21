package pl.micede.personalapi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.TargetCategory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TargetRequestDto {

    @NotBlank
    private String targetName;

    @NotBlank
    private String description;

    @FutureOrPresent
    private LocalDateTime targetBegins;

    @FutureOrPresent
    private LocalDateTime targetEnds;

    @NotNull
    private TargetCategory targetCategory;


    private List<HabitModel> habits;


}
