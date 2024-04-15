package pl.micede.personalapi.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.micede.personalapi.dto.TargetReadDto;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.repository.TargetRepository;
import pl.micede.personalapi.utils.exception.TargetNotFoundException;
import pl.micede.personalapi.utils.mapper.TargetMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class TargetService {

    private final TargetRepository targetRepository;
    private final TargetMapper targetMapper = new TargetMapper();


    /**
     * Creates a new target using details provided in a TargetReqDto object.
     *
     * @param requestDto Data Transfer Object containing target details.
     * @return The saved TargetModel entity.
     */
    public TargetModel addNewTarget(TargetReqDto requestDto) {
        TargetModel targetModel = new TargetModel();
        targetModel.setTargetName(requestDto.getTargetName());
        targetModel.setDescription(requestDto.getDescription());
        targetModel.setTargetBegins(requestDto.getTargetBegins());
        targetModel.setTargetEnds(requestDto.getTargetEnds());
        targetModel.setTargetCategory(requestDto.getTargetCategory());
        targetModel.setHabits(requestDto.getHabits());
        targetModel.setUser(requestDto.getUser());
        return targetRepository.save(targetModel);
    }


    /**
     * Retrieves a specific target by its ID.
     *
     * @param id The ID of the target to be found.
     * @throws TargetNotFoundException if target by its ID could not be found.
     * @return A specific target mapped into DTO to read.
     */
    public TargetReadDto getTargetById(Long id) {
        Optional<TargetModel> optModel = targetRepository.findById(id);
        if (optModel.isPresent()) {
            return targetMapper.toDto(optModel.get());
        } else {
            throw new TargetNotFoundException("Target entity not found");
        }
    }


    /**
     * Retrieves all targets found by category.
     *
     * @param targetCategory Target Category by which targets are to be retrieved.
     * @throws TargetNotFoundException if targets could not be found by the category.
     * @return A list of TargetModel entities, mapped into read dto.
     */
    public List<TargetReadDto> getTargetsByCategory(String targetCategory) {
        List<TargetReadDto> listOfTargets = targetRepository.findAllByTargetCategory(TargetCategory.valueOf(targetCategory))
                .stream().map(targetMapper::toDto)
                .collect(Collectors.toList());
        if (listOfTargets.isEmpty()) {
            throw new TargetNotFoundException(String.format("Target from %s category not found", targetCategory));
        }
        return listOfTargets;
    }


    /**
     * Updates an existing target by its name with new ending date in a TargetModel object.
     *
     * @param targetName The name of the target to be updated.
     * @param newDate New ending date of the target.
     * @throws TargetNotFoundException if targets could not be found by the name.
     * @return The updated TargetModel entity mapped into read dto.
     */
    public TargetReadDto updateTargetEndingDateByName(String targetName, LocalDateTime newDate) {
        Optional<TargetModel> byTargetName = targetRepository.findByTargetName(targetName);
        byTargetName.ifPresent(e -> e.setTargetEnds(newDate));
        return byTargetName.map(targetMapper::toDto)
                .orElseThrow(() -> new TargetNotFoundException(String.format("%s target not found", targetName)));
    }



    /**
     * Deletes a target from the database.
     *
     * @param id The ID of the target to be deleted.
     */
    public void deleteTargetById(Long id) {
        if (!targetRepository.existsById(id)) {
            throw new TargetNotFoundException(String.format("%s target id not found", id));
        }
        targetRepository.deleteById(id);
    }
}
