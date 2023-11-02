package com.active.exerciseservice.exercisemanagment;

import com.active.exerciseservice.exercisemanagment.dto.ExerciseRequest;
import com.active.exerciseservice.exercisemanagment.dto.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
