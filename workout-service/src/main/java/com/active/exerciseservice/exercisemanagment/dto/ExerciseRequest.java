package com.active.exerciseservice.exercisemanagment.dto;

import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExerciseRequest {
    private String title;
    private String description;
    private ExerciseType type;
    private BodyPart bodyPart;
    private Level level;
}
