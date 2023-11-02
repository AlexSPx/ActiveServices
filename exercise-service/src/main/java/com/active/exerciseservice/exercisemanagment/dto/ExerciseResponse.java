package com.active.exerciseservice.exercisemanagment.dto;

import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResponse {
    private String id;
    private String title;
    private String description;
    private String type;
    private String bodyPart;
    private String level;
}
