package com.active.exerciseservice.exerciserecords.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "exercise_records")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Builder
@Data
public class ExerciseRecord {
    private String id;
    private String exerciseId;
    private String exerciseName;
    private boolean isTimeBased;

    public ExerciseRecord(String exerciseId, String exerciseName, boolean isTimeBased){
        this.exerciseId = exerciseId;
        this.isTimeBased = isTimeBased;
        this.exerciseName = exerciseName;
    }

    @Override
    public String toString() {
        return "ExerciseRecord{" +
                "id='" + id + '\'' +
                ", exerciseId='" + exerciseId + '\'' +
                ", exerciseName='" + exerciseName + '\'' +
                ", isTimeBased=" + isTimeBased +
                '}';
    }
}
