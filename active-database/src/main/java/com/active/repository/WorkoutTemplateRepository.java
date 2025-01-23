package com.active.repository;

import com.active.models.workout.template.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate, String> {
}
