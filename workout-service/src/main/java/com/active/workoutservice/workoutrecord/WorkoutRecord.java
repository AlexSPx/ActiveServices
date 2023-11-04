package com.active.workoutservice.workoutrecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("workout_records")
@Data
@Builder
@AllArgsConstructor
public class WorkoutRecord {
    @Id
    private String id;
    private String workoutId;
    private String uid;
    private int duration; // In seconds
    private LocalDateTime finishedAt;
    private String[] exerciseRecordIds;
}
