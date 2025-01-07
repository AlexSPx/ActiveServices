package com.active.services.structures;

import com.active.models.exercise.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class ExerciseSearchStructure {
    private String title;
    private Force force;
    private Level level;
    private Mechanic mechanic;
    private Equipment equipment;
    private List<Muscle> primaryMuscles;
    private List<Muscle> secondaryMuscles;
    private Category category;
    private Pageable pageable;
}
