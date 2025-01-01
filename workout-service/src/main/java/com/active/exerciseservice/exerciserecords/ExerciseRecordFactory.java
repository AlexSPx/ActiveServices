package com.active.exerciseservice.exerciserecords;

import com.active.models.ExerciseRecord;
import com.active.models.ExerciseRecordCardio;
import com.active.models.ExerciseRecordWeight;

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
