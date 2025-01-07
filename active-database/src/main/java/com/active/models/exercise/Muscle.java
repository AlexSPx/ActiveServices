package com.active.models.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Muscle {
    ABDOMINALS("abdominals"),
    ABDUCTORS("abductors"),
    ADDUCTORS("adductors"),
    BICEPS("biceps"),
    CALVES("calves"),
    CHEST("chest"),
    FOREARMS("forearms"),
    GLUTES("glutes"),
    HAMSTRINGS("hamstrings"),
    LATS("lats"),
    LOWER_BACK("lower back"),
    MIDDLE_BACK("middle back"),
    NECK("neck"),
    QUADRICEPS("quadriceps"),
    SHOULDERS("shoulders"),
    TRAPS("traps"),
    TRICEPS("triceps"),
    OTHER("other");

    private final String value;

    Muscle(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static Muscle fromString(String value) {
        for (Muscle muscle : Muscle.values()) {
            if (muscle.value.equalsIgnoreCase(value)) {
                return muscle;
            }
        }

        return OTHER;
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
