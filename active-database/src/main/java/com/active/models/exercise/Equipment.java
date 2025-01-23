package com.active.models.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Equipment {
    BARBELL("barbell"),
    DUMBBELL("dumbbell"),
    KETTLEBELLS("kettlebells"),
    MACHINE("machine"),
    CABLE("cable"),
    BODY_ONLY("body only"),
    BANDS("bands"),
    MEDICINE_BALL("medicine ball"),
    BENCH("bench"),
    FOAM_ROLL("foam roll"),
    EXERCISE_BALL("exercise ball"),
    E_Z_CURL_BAR("e-z curl bar"),
    OTHER("other");

    private final String value;

    Equipment(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static Equipment fromString(String value) {
        for (Equipment equipment : Equipment.values()) {
            if (equipment.value.equalsIgnoreCase(value)) {
                return equipment;
            }
        }
        return OTHER;
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
