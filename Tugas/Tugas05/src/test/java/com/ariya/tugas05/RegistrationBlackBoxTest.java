package com.ariya.tugas05;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationBlackBoxTest {
    private record Case(String id, String scenario, boolean expected, BlackBoxSystem.RegistrationInput input) {
    }

    @TestFactory
    Stream<DynamicTest> registrationCases() {
        List<Case> cases = List.of(
                c("TC-R01", "seluruh input valid", true, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R02", "nama kosong", false, "", "ariya25", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R03", "nama 2 karakter", false, "Ar", "ariya25", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R04", "nama 3 karakter", true, "Ari", "ariya25", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R05", "nama 50 karakter", true, "A".repeat(50), "ariya25", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R06", "nama 51 karakter", false, "A".repeat(51), "ariya25", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R07", "username 4 karakter", false, "Ariya Narendra", "ari1", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R08", "username 5 karakter", true, "Ariya Narendra", "ariya", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R09", "username diawali angka", false, "Ariya Narendra", "1ariya", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R10", "username memakai underscore", false, "Ariya Narendra", "ariya_25", "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R11", "username 20 karakter", true, "Ariya Narendra", "a".repeat(20), "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R12", "username 21 karakter", false, "Ariya Narendra", "a".repeat(21), "ariya@example.com", "Aman1234", 25, "081234567890"),
                c("TC-R13", "email valid", true, "Ariya Narendra", "ariya25", "ariya.narendra@example.co.id", "Aman1234", 25, "081234567890"),
                c("TC-R14", "email tanpa @", false, "Ariya Narendra", "ariya25", "ariya.example.com", "Aman1234", 25, "081234567890"),
                c("TC-R15", "domain email tidak lengkap", false, "Ariya Narendra", "ariya25", "ariya@", "Aman1234", 25, "081234567890"),
                c("TC-R16", "password 7 karakter", false, "Ariya Narendra", "ariya25", "ariya@example.com", "Aa12345", 25, "081234567890"),
                c("TC-R17", "password 8 karakter", true, "Ariya Narendra", "ariya25", "ariya@example.com", "Aa123456", 25, "081234567890"),
                c("TC-R18", "password tanpa huruf besar", false, "Ariya Narendra", "ariya25", "ariya@example.com", "aman1234", 25, "081234567890"),
                c("TC-R19", "password tanpa huruf kecil", false, "Ariya Narendra", "ariya25", "ariya@example.com", "AMAN1234", 25, "081234567890"),
                c("TC-R20", "password tanpa angka", false, "Ariya Narendra", "ariya25", "ariya@example.com", "AmanAman", 25, "081234567890"),
                c("TC-R21", "umur 16", false, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 16, "081234567890"),
                c("TC-R22", "umur 17", true, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 17, "081234567890"),
                c("TC-R23", "umur 100", true, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 100, "081234567890"),
                c("TC-R24", "umur 101", false, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 101, "081234567890"),
                c("TC-R25", "telepon 9 digit", false, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 25, "081234567"),
                c("TC-R26", "telepon 10 digit", true, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 25, "0812345678"),
                c("TC-R27", "telepon 13 digit", true, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 25, "0812345678901"),
                c("TC-R28", "telepon 14 digit", false, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 25, "08123456789012"),
                c("TC-R29", "telepon tidak diawali 08", false, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 25, "071234567890"),
                c("TC-R30", "telepon mengandung huruf", false, "Ariya Narendra", "ariya25", "ariya@example.com", "Aman1234", 25, "08123A567890")
        );

        return cases.stream().map(testCase -> DynamicTest.dynamicTest(
                testCase.id() + " - " + testCase.scenario(),
                () -> {
                    ValidationResult actual = BlackBoxSystem.validateRegistration(testCase.input());
                    assertEquals(testCase.expected(), actual.valid(), actual.message());
                }));
    }

    private static Case c(
            String id,
            String scenario,
            boolean expected,
            String name,
            String username,
            String email,
            String password,
            int age,
            String phone) {
        return new Case(id, scenario, expected,
                new BlackBoxSystem.RegistrationInput(name, username, email, password, age, phone));
    }
}
