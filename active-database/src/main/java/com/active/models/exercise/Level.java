package com.active.models.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Level {
    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    EXPERT("expert"),
    NONE("none");

    private final String value;

    Level(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Level fromString(String value) {
        for (Level level : Level.values()) {
            if (level.value.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid value for Level: " + value);
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}