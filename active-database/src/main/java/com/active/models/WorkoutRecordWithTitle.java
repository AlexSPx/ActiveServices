package com.active.models;

import com.active.models.workout.WorkoutRecord;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class WorkoutRecordWithTitle extends WorkoutRecord {
    private String workoutTitle;

    public WorkoutRecordWithTitle(String id, Long timeToComplete, String workoutTitle, List<ExerciseRecord> exerciseRecords, LocalDateTime createdAt) {
        super.setId(id);
        super.setTimeToComplete(timeToComplete);
        super.setExerciseRecords(exerciseRecords);
        super.setCreatedAt(createdAt);
        this.workoutTitle = workoutTitle;
    }
}
