package com.active.models.exercise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Category {
    POWERLIFTING("powerlifting"),
    STRENGTH("strength"),
    STRETCHING("stretching"),
    CARDIO("cardio"),
    OLYMPIC_WEIGHTLIFTING("olympic weightlifting"),
    STRONGMAN("strongman"),
    PLYOMETRICS("plyometrics"),
    OTHER("other");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static Category fromString(String value) {
        for (Category category : Category.values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }

        return OTHER;
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
