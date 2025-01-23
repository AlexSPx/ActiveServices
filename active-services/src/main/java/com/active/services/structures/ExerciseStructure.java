package com.active.services.structures;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseStructure {
    private String exerciseId;
    private List<Integer> repetitions;
    private List<Double> weights;
    private List<Integer> durations;
}
