package com.ariya.tugas05;

public record ValidationResult(boolean valid, String message) {
    public static ValidationResult accepted(String message) {
        return new ValidationResult(true, message);
    }

    public static ValidationResult rejected(String message) {
        return new ValidationResult(false, message);
    }
}
