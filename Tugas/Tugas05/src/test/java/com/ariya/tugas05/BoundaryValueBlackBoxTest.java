package com.ariya.tugas05;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoundaryValueBlackBoxTest {
    private record Case(String id, String scenario, boolean expected, Supplier<ValidationResult> action) {
    }

    @TestFactory
    Stream<DynamicTest> studentBoundaries() {
        List<Case> cases = new ArrayList<>();
        double[] scores = {-1, 0, 1, 99, 100, 101};
        boolean[] scoreExpected = {false, true, true, true, true, false};
        for (int index = 0; index < scores.length; index++) {
            double score = scores[index];
            boolean expected = scoreExpected[index];
            cases.add(new Case("B3-N" + (index + 1), "nilai ujian = " + score, expected,
                    () -> BlackBoxSystem.validateStudent(score, 3.0, 5)));
        }

        double[] gpas = {-0.1, 0.0, 0.1, 3.9, 4.0, 4.1};
        boolean[] gpaExpected = {false, true, true, true, true, false};
        for (int index = 0; index < gpas.length; index++) {
            double gpa = gpas[index];
            boolean expected = gpaExpected[index];
            cases.add(new Case("B3-I" + (index + 1), "IPK = " + gpa, expected,
                    () -> BlackBoxSystem.validateStudent(80, gpa, 5)));
        }

        int[] semesters = {0, 1, 2, 13, 14, 15};
        boolean[] semesterExpected = {false, true, true, true, true, false};
        for (int index = 0; index < semesters.length; index++) {
            int semester = semesters[index];
            boolean expected = semesterExpected[index];
            cases.add(new Case("B3-S" + (index + 1), "semester = " + semester, expected,
                    () -> BlackBoxSystem.validateStudent(80, 3.0, semester)));
        }
        return toDynamicTests(cases);
    }

    @TestFactory
    Stream<DynamicTest> transferBoundaries() {
        List<Case> cases = List.of(
                transfer("B4-01", "nominal min-1", false, 9_999, 200_000_000),
                transfer("B4-02", "nominal min", true, 10_000, 200_000_000),
                transfer("B4-03", "nominal min+1", true, 10_001, 200_000_000),
                transfer("B4-04", "nominal max-1", true, 99_999_999, 200_000_000),
                transfer("B4-05", "nominal max", true, 100_000_000, 200_000_000),
                transfer("B4-06", "nominal max+1", false, 100_000_001, 200_000_000),
                transfer("B4-07", "sisa saldo 49.999", false, 10_000, 62_499),
                transfer("B4-08", "sisa saldo 50.000", true, 10_000, 62_500),
                transfer("B4-09", "sisa saldo 50.001", true, 10_000, 62_501)
        );
        return toDynamicTests(cases);
    }

    private static Case transfer(
            String id, String scenario, boolean expected, long amount, long balance) {
        return new Case(id, scenario, expected,
                () -> BlackBoxSystem.validateTransfer(amount, balance));
    }

    private static Stream<DynamicTest> toDynamicTests(List<Case> cases) {
        return cases.stream().map(testCase -> DynamicTest.dynamicTest(
                testCase.id() + " - " + testCase.scenario(),
                () -> {
                    ValidationResult actual = testCase.action().get();
                    assertEquals(testCase.expected(), actual.valid(), actual.message());
                }));
    }
}
