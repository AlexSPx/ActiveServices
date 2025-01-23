package com.active.repository;

import com.active.models.workout.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, String> {
    Page<Workout> findByUser_Id(String userId, Pageable pageable);
}
