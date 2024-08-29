package com.active.exerciseservice.exercisemanagment;

import com.active.exerciseservice.exercisemanagment.dto.ExerciseRequest;
import com.active.exerciseservice.exercisemanagment.dto.ExerciseResponse;
import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createExercise(@RequestBody ExerciseRequest exerciseRequest) {
        return exerciseService.createExercise(exerciseRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseResponse> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @PostMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseModel> getAllByIds(@RequestBody List<String> ids) {
        return exerciseService.getAllByIds(ids);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ExerciseResponse> searchExercises(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) ExerciseType type,
            @RequestParam(required = false) BodyPart bodyPart,
            @RequestParam(required = false) Level level,
            @RequestParam(required = false, defaultValue = "0") int page

    ) {
        return exerciseService
                .searchExercises(title, description, type, bodyPart, level, page, 10);
    }
}
