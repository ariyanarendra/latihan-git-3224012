package com.ariya.tugas04;

public class StudentGrade {
    public String calculateGrade(int score) {
        validateScore(score);
        if (score >= 80) {
            return "A";
        } else if (score >= 70) {
            return "B";
        } else if (score >= 60) {
            return "C";
        } else if (score >= 50) {
            return "D";
        }
        return "E";
    }

    public boolean isPassed(int score) {
        validateScore(score);
        return score >= 60;
    }

    private void validateScore(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
    }
}
