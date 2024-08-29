package com.active.exerciseservice.exercisemanagment.repository;

import com.active.exerciseservice.exercisemanagment.ExerciseModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExerciseRepository extends MongoRepository<ExerciseModel, String>, CustomExerciseRepository {
}
