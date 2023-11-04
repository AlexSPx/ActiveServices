package com.active.workoutservice.workout.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("workout")
@Data
@Builder
@AllArgsConstructor
public class Workout {
    @Id
    private String id;
    private WorkoutStructure workoutStructure;
    private String title;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
