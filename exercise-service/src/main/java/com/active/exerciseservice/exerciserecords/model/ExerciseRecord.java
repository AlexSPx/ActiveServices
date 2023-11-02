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
    private boolean isTimeBased;

    public ExerciseRecord(String exerciseId, boolean isTimeBased){
        this.exerciseId = exerciseId;
        this.isTimeBased = isTimeBased;
    }

}
