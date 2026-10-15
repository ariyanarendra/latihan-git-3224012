package com.ariya.tugas05;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ProductApiSimulator {
    private static final Set<String> CATEGORIES = Set.of("electronics", "clothing", "food");

    private ProductApiSimulator() {
    }

    public record ApiResponse(int status, String message) {
    }

    public static ApiResponse postProduct(String token, Map<String, Object> body) {
        if (!"valid-token".equals(token)) {
            return new ApiResponse(401, "Unauthorized: token tidak valid");
        }

        List<String> errors = new ArrayList<>();
        Object name = body.get("name");
        Object price = body.get("price");
        Object stock = body.get("stock");
        Object category = body.get("category");

        if (!(name instanceof String value) || value.length() < 3 || value.length() > 100) {
            errors.add("name harus 3-100 karakter");
        }
        if (!(price instanceof Number value)
                || value.doubleValue() < 1_000
                || value.doubleValue() > 10_000_000) {
            errors.add("price harus 1000-10000000");
        }
        if (!(stock instanceof Integer value) || value < 0 || value > 1_000) {
            errors.add("stock harus 0-1000");
        }
        if (!(category instanceof String value) || !CATEGORIES.contains(value)) {
            errors.add("category tidak tersedia");
        }

        if (!errors.isEmpty()) {
            return new ApiResponse(400, "Bad Request: " + String.join("; ", errors));
        }
        return new ApiResponse(201, "Created: product berhasil dibuat");
    }
}
