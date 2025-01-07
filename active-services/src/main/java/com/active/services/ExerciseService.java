package com.active.services;

import com.active.models.Exercise;
import com.active.repository.ExerciseRepository;
import com.active.services.structures.ExerciseSearchStructure;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Page<Exercise> searchExercise(ExerciseSearchStructure exerciseSearchStructure) {
        return exerciseRepository.findByCriteria(
                exerciseSearchStructure.getTitle(),
                exerciseSearchStructure.getForce(),
                exerciseSearchStructure.getLevel(),
                exerciseSearchStructure.getMechanic(),
                exerciseSearchStructure.getEquipment(),
                exerciseSearchStructure.getPrimaryMuscles(),
                exerciseSearchStructure.getSecondaryMuscles(),
                exerciseSearchStructure.getCategory(),
                exerciseSearchStructure.getPageable()
        );
    }
}
