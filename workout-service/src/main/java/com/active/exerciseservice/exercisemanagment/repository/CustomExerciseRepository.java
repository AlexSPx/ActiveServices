package com.active.exerciseservice.exercisemanagment.repository;

import com.active.exerciseservice.exercisemanagment.ExerciseModel;
import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomExerciseRepository {
    Page<ExerciseModel> findByCriteria(
            String title,
            String description,
            ExerciseType type,
            BodyPart bodyPart,
            Level level,
            Pageable pageable
    );
}
