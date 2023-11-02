package com.active.exerciseservice.exercisemanagment;

import com.active.exerciseservice.types.BodyPart;
import com.active.exerciseservice.types.ExerciseType;
import com.active.exerciseservice.types.Level;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "exercise")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExerciseModel {
    @Id
    private String id;
    private String title;
    private String description;
    private ExerciseType type;
    private BodyPart bodyPart;
    private Level level;
}

