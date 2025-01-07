package com.active.services;

import com.active.models.Exercise;
import com.active.models.workout.template.TemplateExercise;
import com.active.models.workout.template.TemplateExerciseCardio;
import com.active.models.workout.template.TemplateExerciseWeight;
import com.active.models.workout.template.WorkoutTemplate;
import com.active.repository.ExerciseRepository;
import com.active.repository.TemplateExerciseRepository;
import com.active.repository.WorkoutRepository;
import com.active.repository.WorkoutTemplateRepository;
import com.active.services.structures.ExerciseStructure;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkoutTemplateService {
    private final WorkoutTemplateRepository workoutTemplateRepository;
    private final TemplateExerciseRepository templateExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;

    @Transactional
    public WorkoutTemplate createWorkoutTemplate(List<ExerciseStructure> templateExerciseStructures) {
        Map<String, Exercise> exerciseMap = exerciseRepository.findAllById(templateExerciseStructures.stream().map(ExerciseStructure::getExerciseId).toList())
                .stream()
                .collect(Collectors.toMap(Exercise::getId, Function.identity()));

        WorkoutTemplate workoutTemplate = WorkoutTemplate.builder()
                .build();

        workoutTemplateRepository.save(workoutTemplate);

        List<TemplateExercise> templateExercises = templateExerciseStructures.stream()
                .map(templateExerciseStructure -> {
                    Exercise exercise = exerciseMap.get(templateExerciseStructure.getExerciseId());
                    TemplateExercise templateExercise = createTemplateExercise(exercise, templateExerciseStructure.getRepetitions(), templateExerciseStructure.getWeights(), templateExerciseStructure.getDurations());
                    templateExercise.setWorkoutTemplate(workoutTemplate);
                    return templateExercise;
                })
                .toList();

        templateExerciseRepository.saveAll(templateExercises);
        workoutTemplate.setTemplateExercises(templateExercises);
        return workoutTemplate;
    }

    private TemplateExercise createTemplateExercise(Exercise exercise, List<Integer> repetitions, List<Double> weights, List<Integer> durations) {
        if(durations == null) {
            return TemplateExerciseWeight.builder()
                    .exercise(exercise)
                    .repetitions(repetitions)
                    .weights(weights)
                    .build();
        } else {
            return TemplateExerciseCardio.builder()
                    .exercise(exercise)
                    .durations(durations)
                    .build();
        }
    }
}
