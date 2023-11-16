package com.active.exerciseservice.exercisemanagment.repository;

import com.active.exerciseservice.exercisemanagment.ExerciseModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExerciseRepository extends MongoRepository<ExerciseModel, String>, CustomExerciseRepository {
}
