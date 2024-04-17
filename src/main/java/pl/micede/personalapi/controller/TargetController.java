package pl.micede.personalapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
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

    /**
     * Creates a new Target based on the provided TargetReqDto object.
     *
     * @param requestDto The target data transfer object containing the target details.
     * @return ResponseEntity containing the saved TargetModel object and http status Created.
     */
    @Operation(summary = "Create Target", description = "Creates a new Target based on the provided data")
    @PostMapping("/add")
    public ResponseEntity<TargetModel> addNewTarget(@Valid @RequestBody TargetReqDto requestDto) {
        TargetModel targetModel = targetService.addNewTarget(requestDto);
        return new ResponseEntity<>(targetModel, HttpStatus.CREATED);
    }


    /**
     * Retrieves a specific Target by its ID with information converted into ReadDto.
     *
     * @param id The ID of the target to retrieve.
     * @return ResponseEntity containing the TargetReadDto if object is found.
     */
    @Operation(summary = "Get Target by ID", description = "Retrieves target by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<TargetReadDto> getTargetById(@Valid @PathVariable Long id){
        TargetReadDto targetById = targetService.getTargetById(id);
        return ResponseEntity.ok(targetById);
    }

    /**
     * Retrieves all Targets by its category with information converted into ReadDto.
     *
     * @param targetCategory The category of the targets to retrieve.
     * @return ResponseEntity containing the list of TargetReadDto if objects are found.
     */
    @Operation(summary = "Get Targets by Category", description = "Retrieves all Targets by theirs Category")
    @GetMapping("/targetsByCategory/{targetCategory}")
    public ResponseEntity<List<TargetReadDto>> getTargetsByCategory(@Valid @PathVariable String targetCategory){
        List<TargetReadDto> targetsByCategory = targetService.getTargetsByCategory(targetCategory);
        return ResponseEntity.ok(targetsByCategory);
    }

    /**
     * Retrieves all Targets by targetBegins date between given range of dates with information converted into ReadDto.
     *
     * @param minDate The minimal date range of the targets to retrieve.
     * @param maxDate The maximal date range of the targets to retrieve.
     * @return ResponseEntity containing the list of TargetReadDto if objects are found.
     */
    @Operation(summary = "Get Targets with begin date between", description = "Retrieves all Targets by begin date between two dates")
    @GetMapping("/targetsBeginsBetween")
    public ResponseEntity<List<TargetReadDto>> getTargetsWithDateBetween(@RequestParam LocalDateTime minDate, @RequestParam LocalDateTime maxDate) {
        List<TargetReadDto> targetsBeginsBetweenDates = targetService.findTargetsBeginsBetweenDates(minDate, maxDate);
        return ResponseEntity.ok(targetsBeginsBetweenDates);
    }



    /**
     * Updates a specific target by its name and changes its end date.
     *
     * @param targetName The name of the target to retrieve.
     * @param newDate New ending date for target update provided in request param.
     * @return ResponseEntity containing the updated TargetReadDto if object is found.
     */
    @Operation(summary = "Update Target", description = "Updates an existing target with new data")
    @PatchMapping("/updateEndingDate/{targetName}")
    public ResponseEntity<TargetReadDto> updateTargetEndingDateByName(@Valid @PathVariable String targetName, @RequestParam LocalDateTime newDate) {
        TargetReadDto targetReadDto = targetService.updateTargetEndingDateByName(targetName, newDate);
        return ResponseEntity.ok(targetReadDto);
    }

    /**
     * Deletes a specific Target by its ID.
     *
     * @param id The ID of the target to retrieve.
     * @return ResponseEntity with NO_CONTENT status if the deletion was successful.
     */
    @Operation(summary = "Delete Target", description = "Deletes target by its ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTargetById(@Valid @PathVariable Long id){
        targetService.deleteTargetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
