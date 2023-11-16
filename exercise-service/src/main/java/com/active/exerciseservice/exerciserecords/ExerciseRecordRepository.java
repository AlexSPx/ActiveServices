package com.active.exerciseservice.exerciserecords;

import com.active.exerciseservice.exerciserecords.model.ExerciseRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExerciseRecordRepository extends MongoRepository<ExerciseRecord, String> {
}
