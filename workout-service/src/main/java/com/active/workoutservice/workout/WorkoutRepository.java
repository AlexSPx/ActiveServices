package com.active.workoutservice.workout;

import com.active.workoutservice.workout.models.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkoutRepository extends MongoRepository<Workout, String> {
    Page<Workout> findByCreatedBy(String uid, Pageable pageable);

}
