package pl.micede.personalapi.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import pl.micede.personalapi.dto.HabitReadDto;
import pl.micede.personalapi.dto.HabitReqDto;
import pl.micede.personalapi.model.HabitModel;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.repository.HabitRepository;
import pl.micede.personalapi.repository.TargetRepository;
import pl.micede.personalapi.utils.exception.HabitNotFoundException;
import pl.micede.personalapi.utils.exception.TargetNotFoundException;
import pl.micede.personalapi.utils.mapper.HabitMapper;

import java.util.Optional;

@Service
@Data
public class HabitService {

    private final HabitRepository habitRepository;
    private final TargetRepository targetRepository;
    private final HabitMapper habitMapper;

    public HabitModel addNewHabit(HabitReqDto habitDto) {

        HabitModel habitModel = new HabitModel();
        habitModel.setHabitName(habitDto.getHabitName());
        habitModel.setHabitDescription(habitDto.getHabitDescription());
        habitModel.setFrequencyType(habitDto.getFrequencyType());
        Optional<TargetModel> foundTargetById = targetRepository.findById(habitDto.getTarget().getId());
        if (foundTargetById.isEmpty()) {
            throw new TargetNotFoundException("Target not found");
        }
        habitModel.setTarget(foundTargetById.get());
        habitModel.setActivities(habitDto.getActivities());
        return habitRepository.save(habitModel);
    }

    public HabitReadDto findById(Long id) {
        return habitRepository.findById(id)
                .map(habitMapper::toDto)
                .orElseThrow(() -> new HabitNotFoundException("Habit not found"));
    }

    public HabitReadDto findByName(String habitName) {
        return habitRepository.findByHabitName(habitName)
                .map(habitMapper::toDto)
                .orElseThrow(() -> new HabitNotFoundException(String.format("%s habit not found",habitName)));
    }

    public HabitReadDto updateDescriptionById(Long id, String newDescription) {
        Optional<HabitModel> habitById = habitRepository.findById(id);
        habitById.ifPresent(e -> e.setHabitDescription(newDescription));
        return habitById.map(habitMapper::toDto)
                .orElseThrow(() -> new HabitNotFoundException(String.format("Habit with %d id not found", id)));
    }

    public void deleteById(Long id) {
        if (!habitRepository.existsById(id)) {
            throw new HabitNotFoundException("Habit not found");
        }
        habitRepository.deleteById(id);
    }
}
