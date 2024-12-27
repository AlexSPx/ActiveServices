package com.active.exerciseservice.exerciserecords;

import com.active.exerciseservice.exerciserecords.model.ExerciseRecord;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecordCardio;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecordWeight;

public class ExerciseRecordFactory {
    public static ExerciseRecord createExerciseRecord(
            String exerciseName, boolean isTimeBased,
            int[] reps, double[] weight, double[] time) {
        if (isTimeBased) {
            return ExerciseRecordCardio.builder()
                    .exerciseName(exerciseName)
                    .isTimeBased(true)
                    .time(time)
                    .build();
        } else {
            return ExerciseRecordWeight.builder()
                    .exerciseName(exerciseName)
                    .isTimeBased(false)
                    .reps(reps)
                    .weight(weight)
                    .build();
        }
    }
}
