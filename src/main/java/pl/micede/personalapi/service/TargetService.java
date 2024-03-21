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
    private final TargetMapper targetMapper;

    public TargetModel addNewTarget(TargetReqDto requestDto) {
        TargetModel targetModel = new TargetModel();
        targetModel.setTargetName(requestDto.getTargetName());
        targetModel.setDescription(requestDto.getDescription());
        targetModel.setTargetBegins(requestDto.getTargetBegins());
        targetModel.setTargetEnds(requestDto.getTargetEnds());
        targetModel.setTargetCategory(requestDto.getTargetCategory());
        targetModel.setHabits(requestDto.getHabits());
        return targetRepository.save(targetModel);
    }

    public TargetReadDto getTargetById(Long id) {
        return targetRepository.findById(id)
                .map(targetMapper::toDto)
                .orElseThrow(() -> new TargetNotFoundException("Target entity not found"));
    }


    public List<TargetReadDto> getTargetsByCategory(String targetCategory) {
        List<TargetReadDto> listOfTargets = targetRepository.findAllByTargetCategory(TargetCategory.valueOf(targetCategory))
                .stream().map(targetMapper::toDto)
                .collect(Collectors.toList());
        if (listOfTargets.isEmpty()) {
            throw new TargetNotFoundException(String.format("Target from %s category not found", targetCategory));
        }
        return listOfTargets;
    }

    public TargetReadDto updateTargetEndingDateByName(String targetName, LocalDateTime newDate) {
        Optional<TargetModel> byTargetName = targetRepository.findByTargetName(targetName);
        byTargetName.ifPresent(e -> e.setTargetEnds(newDate));
        return byTargetName.map(targetMapper::toDto)
                .orElseThrow(() -> new TargetNotFoundException(String.format("%s target not found", targetName)));
    }

    public void deleteTargetById(Long id) {
        if (!targetRepository.existsById(id)) {
            throw new TargetNotFoundException(String.format("%s target id not found", id));
        }
        targetRepository.deleteById(id);
    }
}
