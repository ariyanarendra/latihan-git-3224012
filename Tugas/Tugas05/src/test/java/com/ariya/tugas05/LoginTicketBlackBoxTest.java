package com.ariya.tugas05;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginTicketBlackBoxTest {
    private static final LocalDate TODAY = LocalDate.of(2026, 10, 15);

    private record Case(String id, String scenario, boolean expected, Supplier<ValidationResult> action) {
    }

    @TestFactory
    Stream<DynamicTest> loginEquivalencePartitions() {
        List<Case> cases = List.of(
                login("A1-01", "seluruh input valid", true, "Ariya25", "Aman1234", 25),
                login("A1-02", "username ari1 hanya 4 karakter", false, "ari1", "Aman1234", 25),
                login("A1-03", "username 21 karakter", false, "user12345678901234567", "Aman1234", 25),
                login("A1-04", "username memakai underscore", false, "ariya_25", "Aman1234", 25),
                login("A1-05", "password valid", true, "Ariya25", "Aman1234", 25),
                login("A1-06", "password 7 karakter", false, "Ariya25", "Am12345", 25),
                login("A1-07", "password tanpa huruf besar", false, "Ariya25", "aman1234", 25),
                login("A1-08", "password tanpa huruf kecil", false, "Ariya25", "AMAN1234", 25),
                login("A1-09", "password tanpa angka", false, "Ariya25", "AmanAman", 25),
                login("A1-10", "umur 17", true, "Ariya25", "Aman1234", 17),
                login("A1-11", "umur 16", false, "Ariya25", "Aman1234", 16),
                login("A1-12", "umur 101", false, "Ariya25", "Aman1234", 101)
        );
        return toDynamicTests(cases);
    }

    @TestFactory
    Stream<DynamicTest> ticketEquivalencePartitions() {
        List<Case> cases = List.of(
                ticket("A2-01", "jumlah tiket 5", true, 5, "", "2026-10-15"),
                ticket("A2-02", "jumlah tiket 0", false, 0, "", "2026-10-15"),
                ticket("A2-03", "jumlah tiket 11", false, 11, "", "2026-10-15"),
                ticket("A2-04", "kode promo kosong", true, 2, "", "2026-10-15"),
                ticket("A2-05", "kode promo 7 karakter", true, 2, "HEMAT10", "2026-10-15"),
                ticket("A2-06", "kode promo 3 karakter", false, 2, "HEM", "2026-10-15"),
                ticket("A2-07", "kode promo 11 karakter", false, 2, "PROMOHEMAT1", "2026-10-15"),
                ticket("A2-08", "tanggal hari ini", true, 2, "", "2026-10-15"),
                ticket("A2-09", "tanggal masa depan", true, 2, "", "2026-10-20"),
                ticket("A2-10", "tanggal sudah lewat", false, 2, "", "2026-10-14"),
                ticket("A2-11", "format tanggal salah", false, 2, "", "15-10-2026"),
                ticket("A2-12", "tanggal kalender tidak ada", false, 2, "", "2026-02-30")
        );
        return toDynamicTests(cases);
    }

    private static Case login(
            String id, String scenario, boolean expected, String username, String password, int age) {
        return new Case(id, scenario, expected,
                () -> BlackBoxSystem.validateLogin(username, password, age));
    }

    private static Case ticket(
            String id,
            String scenario,
            boolean expected,
            int quantity,
            String promo,
            String date) {
        return new Case(id, scenario, expected,
                () -> BlackBoxSystem.validateTicket(quantity, promo, date, TODAY));
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
