package com.active.repository;

import com.active.models.WorkoutRecordWithTitle;
import com.active.models.workout.WorkoutRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, String> {
    Page<WorkoutRecord> findAllByWorkout_User_Id(String workoutUserId, Pageable pageable);

    @Query("SELECT new com.active.models.WorkoutRecordWithTitle(wr.id, wr.timeToComplete, w.title, wr.exerciseRecords, wr.createdAt) " +
            "FROM WorkoutRecord wr " +
            "JOIN wr.workout w " +
            "WHERE w.user.id = :uid")
    List<WorkoutRecordWithTitle> findAllWithWorkoutTitleForUser(@Param("uid") String uid);
}
