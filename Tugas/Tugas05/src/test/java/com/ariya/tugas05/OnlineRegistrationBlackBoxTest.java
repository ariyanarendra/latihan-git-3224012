package com.ariya.tugas05;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OnlineRegistrationBlackBoxTest {
    private static final LocalDate REFERENCE_DATE = LocalDate.of(2026, 10, 15);

    private record Case(
            String id,
            String scenario,
            boolean expected,
            BlackBoxSystem.OnlineRegistrationInput input) {
    }

    @TestFactory
    Stream<DynamicTest> onlineRegistrationCases() {
        List<Case> cases = List.of(
                c("TC-B01", "seluruh input valid", true, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B02", "nama kosong", false, "", "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B03", "nama 2 karakter", false, "Ar", "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B04", "nama 3 karakter", true, "Ari", "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B05", "nama 100 karakter", true, "A".repeat(100), "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B06", "nama 101 karakter", false, "A".repeat(101), "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B07", "email valid", true, "Ariya Narendra", "ariya.narendra@example.co.id", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B08", "email tanpa @", false, "Ariya Narendra", "ariya.example.com", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B09", "domain email tidak lengkap", false, "Ariya Narendra", "ariya@", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B10", "no. HP 9 digit", false, "Ariya Narendra", "ariya@example.com", "081234567", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B11", "no. HP 10 digit", true, "Ariya Narendra", "ariya@example.com", "0812345678", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B12", "no. HP 13 digit", true, "Ariya Narendra", "ariya@example.com", "0812345678901", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B13", "no. HP 14 digit", false, "Ariya Narendra", "ariya@example.com", "08123456789012", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B14", "no. HP tidak diawali 08", false, "Ariya Narendra", "ariya@example.com", "071234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B15", "no. HP mengandung huruf", false, "Ariya Narendra", "ariya@example.com", "08123A567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B16", "format tanggal salah", false, "Ariya Narendra", "ariya@example.com", "081234567890", "15-01-2000", "Aman123!", "Aman123!"),
                c("TC-B17", "tanggal kalender tidak ada", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-02-30", "Aman123!", "Aman123!"),
                c("TC-B18", "satu hari belum berumur 17", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2009-10-16", "Aman123!", "Aman123!"),
                c("TC-B19", "tepat berumur 17", true, "Ariya Narendra", "ariya@example.com", "081234567890", "2009-10-15", "Aman123!", "Aman123!"),
                c("TC-B20", "satu hari lebih tua dari 17", true, "Ariya Narendra", "ariya@example.com", "081234567890", "2009-10-14", "Aman123!", "Aman123!"),
                c("TC-B21", "password 7 karakter", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "Aa1!bbb", "Aa1!bbb"),
                c("TC-B22", "password 8 karakter", true, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "Aa1!bbbb", "Aa1!bbbb"),
                c("TC-B23", "password tanpa huruf besar", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "aman123!", "aman123!"),
                c("TC-B24", "password tanpa huruf kecil", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "AMAN123!", "AMAN123!"),
                c("TC-B25", "password tanpa angka", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "AmanPass!", "AmanPass!"),
                c("TC-B26", "password tanpa simbol", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "Aman1234", "Aman1234"),
                c("TC-B27", "konfirmasi berbeda", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", "Aman124!"),
                c("TC-B28", "konfirmasi kosong", false, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", ""),
                c("TC-B29", "konfirmasi sama", true, "Ariya Narendra", "ariya@example.com", "081234567890", "2000-01-15", "Aman123!", "Aman123!"),
                c("TC-B30", "email salah dan password tanpa simbol", false, "Ariya Narendra", "ariya.example.com", "081234567890", "2000-01-15", "Aman1234", "Aman1234")
        );

        return cases.stream().map(testCase -> DynamicTest.dynamicTest(
                testCase.id() + " - " + testCase.scenario(),
                () -> {
                    ValidationResult actual = BlackBoxSystem.validateOnlineRegistration(
                            testCase.input(), REFERENCE_DATE);
                    assertEquals(testCase.expected(), actual.valid(), actual.message());
                }));
    }

    private static Case c(
            String id,
            String scenario,
            boolean expected,
            String name,
            String email,
            String phone,
            String birthDate,
            String password,
            String confirmation) {
        return new Case(id, scenario, expected,
                new BlackBoxSystem.OnlineRegistrationInput(
                        name, email, phone, birthDate, password, confirmation));
    }
}
