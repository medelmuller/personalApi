package pl.micede.personalapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.micede.personalapi.dto.TargetReadDto;
import pl.micede.personalapi.dto.TargetReqDto;
import pl.micede.personalapi.model.TargetModel;
import pl.micede.personalapi.service.TargetService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/target")
public class TargetController {

    private final TargetService targetService;

    @PostMapping("/add")
    public ResponseEntity<TargetModel> addNewTarget(@Valid @RequestBody TargetReqDto requestDto) {
        TargetModel targetModel = targetService.addNewTarget(requestDto);
        return new ResponseEntity<>(targetModel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TargetReadDto> getTargetById(@Valid @PathVariable Long id){
        TargetReadDto targetById = targetService.getTargetById(id);
        return ResponseEntity.ok(targetById);
    }


    @GetMapping("/targetsByCategory/{targetCategory}")
    public ResponseEntity<List<TargetReadDto>> getTargetsByCategory(@Valid @PathVariable String targetCategory){
        List<TargetReadDto> targetsByCategory = targetService.getTargetsByCategory(targetCategory);
        return ResponseEntity.ok(targetsByCategory);
    }

    @PatchMapping("/updateEndingDate/{targetName}")
    public ResponseEntity<TargetReadDto> updateTargetEndingDateByName(@Valid @PathVariable String targetName, @RequestParam LocalDateTime newDate) {
        TargetReadDto targetReadDto = targetService.updateTargetEndingDateByName(targetName, newDate);
        return ResponseEntity.ok(targetReadDto);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTargetById(@Valid @PathVariable Long id){
        targetService.deleteTargetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
