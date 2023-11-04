package com.active.workoutservice.workout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Workout not found")
public class WorkoutNotFoundException extends Exception{

    public WorkoutNotFoundException() {
        super("Workout not found");
    }
}
