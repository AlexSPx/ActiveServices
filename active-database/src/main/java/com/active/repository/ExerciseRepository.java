package com.active.repository;

import com.active.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, String>, CustomExerciseRepository {
}
