package com.active.exerciseservice.exerciserecords;

import com.active.exerciseservice.exerciserecords.model.ExerciseRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExerciseRecordRepository extends MongoRepository<ExerciseRecord, String> {
}
