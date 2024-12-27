package com.active.web.endpoints;

import com.active.exerciseservice.exercisemanagment.ExerciseModel;
import com.active.exerciseservice.exercisemanagment.ExerciseService;
import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;
import com.active.web.dto.exercise.ExerciseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createExercise(@RequestBody ExerciseRequest exerciseRequest) {
        ExerciseModel exercise = ExerciseModel.builder()
                .title(exerciseRequest.getTitle())
                .description(exerciseRequest.getDescription())
                .type(exerciseRequest.getType())
                .level(exerciseRequest.getLevel())
                .bodyPart(exerciseRequest.getBodyPart())
                .build();

        return exerciseService.createExercise(exercise);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExerciseModel>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @PostMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseModel> getAllByIds(@RequestBody List<String> ids) {
        return exerciseService.getAllByIds(ids);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ExerciseModel>> searchExercises(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) ExerciseType type,
            @RequestParam(required = false) BodyPart bodyPart,
            @RequestParam(required = false) Level level,
            @RequestParam(required = false, defaultValue = "0") int page

    ) {
        List<ExerciseModel> exercises = exerciseService.searchExercises(title, description, type, bodyPart, level, page, 10);

        return ResponseEntity.ok(exercises);
    }
}