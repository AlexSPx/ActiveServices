package com.active.repository;

import com.active.models.workout.WorkoutRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, String> {
    Page<WorkoutRecord> findAllByWorkout_User_Id(String workoutUserId, Pageable pageable);
}
