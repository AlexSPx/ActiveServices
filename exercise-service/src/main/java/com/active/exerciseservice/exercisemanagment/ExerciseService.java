package com.active.exerciseservice.exercisemanagment;

import com.active.exerciseservice.exercisemanagment.dto.ExerciseRequest;
import com.active.exerciseservice.exercisemanagment.dto.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public String createExercise(ExerciseRequest exerciseRequest) {
        ExerciseModel exercise = ExerciseModel.builder()
                .title(exerciseRequest.getTitle())
                .description(exerciseRequest.getDescription())
                .type(exerciseRequest.getType())
                .level(exerciseRequest.getLevel())
                .bodyPart(exerciseRequest.getBodyPart())
                .build();

        String exerciseId = exerciseRepository.save(exercise).getId();

        log.info("Exercise {} - {} is saved", exercise.getId(), exercise.getTitle());

        return exerciseId;
    }

    public List<ExerciseResponse> getAllExercises() {
        List<ExerciseModel> exercises = exerciseRepository.findAll();

        return exercises.stream().map(this::mapToExerciseResponse).toList();
    }

    private ExerciseResponse mapToExerciseResponse(ExerciseModel exercise) {
        return ExerciseResponse.builder()
                .id(exercise.getId())
                .title(exercise.getTitle())
                .description(exercise.getDescription())
                .type(exercise.getType().toString())
                .level(exercise.getLevel().toString())
                .bodyPart(exercise.getBodyPart().toString())
                .build();
    }

}
