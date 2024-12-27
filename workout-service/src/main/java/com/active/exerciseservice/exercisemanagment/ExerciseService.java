package com.active.exerciseservice.exercisemanagment;

import com.active.exerciseservice.exercisemanagment.repository.ExerciseRepository;
import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;
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

    public String createExercise(ExerciseModel exercise) {
        String exerciseId = exerciseRepository.save(exercise).getId();

        log.info("Exercise {} - {} is saved", exercise.getId(), exercise.getTitle());

        return exerciseId;
    }

    public List<ExerciseModel> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public List<ExerciseModel> getAllByIds(List<String> ids) {
        return exerciseRepository.findAllById(ids);
    }

    public List<ExerciseModel> searchExercises(String title,
                                                  String description,
                                                  ExerciseType type,
                                                  BodyPart bodyPart,
                                                  Level level,
                                                  int offset,
                                                  int limit) {
        Pageable page = PageRequest
                .of(offset, limit, Sort.by("title").descending());

        Page<ExerciseModel> exercises = exerciseRepository.
                findByCriteria(title, description, type, bodyPart, level, page);

        return exercises.getContent();
    }
}
