package com.active.exerciseservice.exercisemanagment;

import com.active.exerciseservice.exercisemanagment.ExerciseModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExerciseRepository extends MongoRepository<ExerciseModel, String> {
}
