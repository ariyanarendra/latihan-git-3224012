package com.ariya.tugas05;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductApiBlackBoxTest {
    private record Case(
            String id,
            String scenario,
            int expectedStatus,
            String token,
            Map<String, Object> body) {
    }

    @TestFactory
    Stream<DynamicTest> productApiCases() {
        Map<String, Object> valid = Map.of(
                "name", "Mouse",
                "price", 150_000,
                "stock", 20,
                "category", "electronics");

        List<Case> cases = List.of(
                new Case("API-01", "request valid", 201, "valid-token", valid),
                new Case("API-02", "name kurang dari 3", 400, "valid-token", Map.of("name", "TV", "price", 5_000_000, "stock", 5, "category", "electronics")),
                new Case("API-03", "price kurang dari 1000", 400, "valid-token", Map.of("name", "Roti", "price", 999, "stock", 10, "category", "food")),
                new Case("API-04", "stock lebih dari 1000", 400, "valid-token", Map.of("name", "Kaos", "price", 75_000, "stock", 1001, "category", "clothing")),
                new Case("API-05", "category invalid", 400, "valid-token", Map.of("name", "Buku", "price", 50_000, "stock", 25, "category", "stationery")),
                new Case("API-06", "unauthorized", 401, "", valid),
                new Case("API-07", "price minimum dan stock minimum", 201, "valid-token", Map.of("name", "Air", "price", 1000, "stock", 0, "category", "food")),
                new Case("API-08", "price maksimum dan stock maksimum", 201, "valid-token", Map.of("name", "Laptop", "price", 10_000_000, "stock", 1000, "category", "electronics")),
                new Case("API-09", "price lebih dari maksimum", 400, "valid-token", Map.of("name", "Laptop", "price", 10_000_001, "stock", 1, "category", "electronics")),
                new Case("API-10", "stock kurang dari 0", 400, "valid-token", Map.of("name", "Roti", "price", 12_000, "stock", -1, "category", "food"))
        );

        return cases.stream().map(testCase -> DynamicTest.dynamicTest(
                testCase.id() + " - " + testCase.scenario(),
                () -> {
                    ProductApiSimulator.ApiResponse actual = ProductApiSimulator.postProduct(
                            testCase.token(), testCase.body());
                    assertEquals(testCase.expectedStatus(), actual.status(), actual.message());
                }));
    }
}
