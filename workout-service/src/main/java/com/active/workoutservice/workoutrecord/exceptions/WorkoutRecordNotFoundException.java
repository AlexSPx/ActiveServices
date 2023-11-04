package com.active.workoutservice.workoutrecord.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Workout record not found")
public class WorkoutRecordNotFoundException extends Exception{
    public WorkoutRecordNotFoundException() {
        super("Workout record not found");
    }
}
