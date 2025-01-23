package com.active.models.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Force {
    STATIC("static"),
    PULL("pull"),
    PUSH("push"),
    OTHER("other");

    private final String value;

    Force(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static Force fromString(String value) {
        for (Force force : Force.values()) {
            if (force.value.equalsIgnoreCase(value)) {
                return force;
            }
        }
        return OTHER;
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
