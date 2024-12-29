package com.active.exerciseservice.exerciserecords;

import com.active.exerciseservice.exerciserecords.model.ExerciseRecord;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecordCardio;
import com.active.exerciseservice.exerciserecords.model.ExerciseRecordWeight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseRecordFactoryTest {

    @Test
    void createExerciseRecord_ShouldCreateCardioRecord() {
        String name = "Running";
        boolean isTimeBased = true;
        double[] time = {15.0, 30.0};

        ExerciseRecord result = ExerciseRecordFactory.createExerciseRecord(name, isTimeBased, null, null, time);

        assertInstanceOf(ExerciseRecordCardio.class, result);
        assertEquals("Running", result.getExerciseName());
        assertArrayEquals(time, ((ExerciseRecordCardio) result).getTime());
    }

    @Test
    void createExerciseRecord_ShouldCreateWeightRecord() {
        String name = "Bench Press";
        boolean isTimeBased = false;
        int[] reps = {10, 8, 6};
        double[] weight = {50.0, 60.0, 70.0};

        ExerciseRecord result = ExerciseRecordFactory.createExerciseRecord(name, isTimeBased, reps, weight, null);

        assertInstanceOf(ExerciseRecordWeight.class, result);
        assertEquals("Bench Press", result.getExerciseName());
        assertArrayEquals(reps, ((ExerciseRecordWeight) result).getReps());
        assertArrayEquals(weight, ((ExerciseRecordWeight) result).getWeight());
    }
}
