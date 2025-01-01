package com.active.repository;


import com.active.models.Exercise;
import com.active.models.exercise.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomExerciseRepository {
    Page<Exercise> findByCriteria(
            String title,
            Force force,
            Level level,
            Mechanic mechanic,
            Equipment equipment,
            List<Muscle> primaryMuscles,
            List<Muscle> secondaryMuscles,
            Category category,
            Pageable pageable
    );
}
