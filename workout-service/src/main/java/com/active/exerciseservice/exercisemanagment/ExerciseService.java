package com.active.exerciseservice.exercisemanagment;

import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.models.Exercise;
import com.active.models.exercise.*;
import com.active.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public Long createExercise(Exercise exercise) {
        Long exerciseId = exerciseRepository.save(exercise).getId();

        log.info("Exercise {} - {} is saved", exercise.getId(), exercise.getTitle());

        return exerciseId;
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public List<Exercise> getAllByIds(List<String> ids) {
        return exerciseRepository.findAllById(ids);
    }

    public List<Exercise> searchExercises(String title,
                                          Force force,
                                          Level level,
                                          Mechanic mechanic,
                                          Equipment equipment,
                                          List<Muscle> primaryMuscles,
                                          List<Muscle> secondaryMuscles,
                                          Category category,
                                          int offset,
                                          int limit) {
        Pageable page = PageRequest
                .of(offset, limit, Sort.by("title").descending());

        Page<Exercise> exercises = exerciseRepository.
                findByCriteria(title, force, level, mechanic, equipment, primaryMuscles, secondaryMuscles, category, page);

        return exercises.getContent();
    }
}
