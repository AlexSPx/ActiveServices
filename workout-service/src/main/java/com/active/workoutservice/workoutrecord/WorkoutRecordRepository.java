package com.active.workoutservice.workoutrecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkoutRecordRepository extends MongoRepository<WorkoutRecord, String> {
    Page<WorkoutRecord> findByUid(String uid, Pageable pageable);
}
