package com.active.models.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Mechanic {
    ISOLATION("isolation"),
    COMPOUND("compound"),
    NONE("none");

    private final String value;

    Mechanic(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static Mechanic fromString(String value) {
        for (Mechanic mechanic : Mechanic.values()) {
            if (mechanic.value.equalsIgnoreCase(value)) {
                return mechanic;
            }
        }
        return NONE; // Default to NONE if value is null or invalid
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
