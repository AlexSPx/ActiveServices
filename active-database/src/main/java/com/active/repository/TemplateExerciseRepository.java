package com.active.repository;

import com.active.models.workout.template.TemplateExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateExerciseRepository extends JpaRepository<TemplateExercise, String> {
}
