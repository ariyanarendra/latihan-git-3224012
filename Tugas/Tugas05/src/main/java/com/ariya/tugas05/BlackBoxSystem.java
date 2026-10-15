package com.ariya.tugas05;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class BlackBoxSystem {
    private static final Pattern ALPHANUMERIC = Pattern.compile("[A-Za-z0-9]+");
    private static final Pattern USERNAME = Pattern.compile("[A-Za-z][A-Za-z0-9]{4,19}");
    private static final Pattern EMAIL = Pattern.compile("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    private static final Pattern PHONE = Pattern.compile("08[0-9]{8,11}");
    private static final DateTimeFormatter STRICT_DATE =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

    private BlackBoxSystem() {
    }

    public record RegistrationInput(
            String name,
            String username,
            String email,
            String password,
            int age,
            String phone) {
    }

    public record OnlineRegistrationInput(
            String name,
            String email,
            String phone,
            String birthDate,
            String password,
            String confirmation) {
    }

    public static ValidationResult validateLogin(String username, String password, int age) {
        List<String> errors = new ArrayList<>();
        if (username == null || username.length() < 5 || username.length() > 20
                || !ALPHANUMERIC.matcher(username).matches()) {
            errors.add("username harus 5-20 karakter dan alfanumerik");
        }
        addBasicPasswordErrors(password, errors);
        if (age < 17 || age > 100) {
            errors.add("umur harus 17-100 tahun");
        }
        return finish(errors, "Diterima; login dapat diproses");
    }

    public static ValidationResult validateTicket(
            int quantity,
            String promoCode,
            String departureDate,
            LocalDate today) {
        List<String> errors = new ArrayList<>();
        if (quantity < 1 || quantity > 10) {
            errors.add("jumlah tiket harus 1-10");
        }
        if (promoCode == null || (!promoCode.isEmpty()
                && (promoCode.length() < 5 || promoCode.length() > 10))) {
            errors.add("kode promo harus kosong atau 5-10 karakter");
        }
        LocalDate parsed = parseDate(departureDate);
        if (parsed == null) {
            errors.add("tanggal harus berformat YYYY-MM-DD dan valid");
        } else if (parsed.isBefore(today)) {
            errors.add("tanggal keberangkatan tidak boleh lewat");
        }
        return finish(errors, "Diterima; tiket dapat dipesan");
    }

    public static ValidationResult validateStudent(double score, double gpa, int semester) {
        List<String> errors = new ArrayList<>();
        if (score < 0 || score > 100) {
            errors.add("nilai ujian harus 0-100");
        }
        if (gpa < 0.0 || gpa > 4.0) {
            errors.add("IPK harus 0.0-4.0");
        }
        if (semester < 1 || semester > 14) {
            errors.add("semester harus 1-14");
        }
        return finish(errors, "Diterima; data mahasiswa valid");
    }

    public static ValidationResult validateTransfer(long amount, long balance) {
        List<String> errors = new ArrayList<>();
        long remaining = balance - amount - 2_500;
        if (amount < 10_000 || amount > 100_000_000) {
            errors.add("nominal harus Rp10.000-Rp100.000.000");
        }
        if (remaining < 50_000) {
            errors.add("sisa saldo minimal Rp50.000");
        }
        return finish(errors, "Diterima; sisa saldo Rp" + remaining);
    }

    public static ValidationResult validateRegistration(RegistrationInput input) {
        List<String> errors = new ArrayList<>();
        if (input.name() == null || input.name().length() < 3 || input.name().length() > 50) {
            errors.add("nama harus 3-50 karakter");
        }
        if (input.username() == null || !USERNAME.matcher(input.username()).matches()) {
            errors.add("username harus 5-20 alfanumerik dan diawali huruf");
        }
        if (!isValidEmail(input.email())) {
            errors.add("format email tidak valid");
        }
        addBasicPasswordErrors(input.password(), errors);
        if (input.age() < 17 || input.age() > 100) {
            errors.add("umur harus 17-100 tahun");
        }
        if (input.phone() == null || !PHONE.matcher(input.phone()).matches()) {
            errors.add("telepon harus 10-13 digit dan diawali 08");
        }
        return finish(errors, "Diterima; registrasi berhasil");
    }

    public static ValidationResult validateOnlineRegistration(
            OnlineRegistrationInput input,
            LocalDate referenceDate) {
        List<String> errors = new ArrayList<>();
        if (input.name() == null || input.name().length() < 3 || input.name().length() > 100) {
            errors.add("nama harus 3-100 karakter");
        }
        if (!isValidEmail(input.email())) {
            errors.add("format email tidak valid");
        }
        if (input.phone() == null || !PHONE.matcher(input.phone()).matches()) {
            errors.add("no. HP harus 10-13 digit dan diawali 08");
        }
        LocalDate birthDate = parseDate(input.birthDate());
        if (birthDate == null) {
            errors.add("tanggal lahir tidak valid");
        } else if (Period.between(birthDate, referenceDate).getYears() < 17) {
            errors.add("umur minimal 17 tahun");
        }
        addStrongPasswordErrors(input.password(), errors);
        if (input.confirmation() == null || !input.confirmation().equals(input.password())) {
            errors.add("konfirmasi password harus sama");
        }
        return finish(errors, "Diterima; pendaftaran berhasil");
    }

    private static void addBasicPasswordErrors(String password, List<String> errors) {
        if (password == null || password.length() < 8) {
            errors.add("password minimal 8 karakter");
        }
        if (password == null || password.chars().noneMatch(Character::isUpperCase)) {
            errors.add("password perlu huruf besar");
        }
        if (password == null || password.chars().noneMatch(Character::isLowerCase)) {
            errors.add("password perlu huruf kecil");
        }
        if (password == null || password.chars().noneMatch(Character::isDigit)) {
            errors.add("password perlu angka");
        }
    }

    private static void addStrongPasswordErrors(String password, List<String> errors) {
        addBasicPasswordErrors(password, errors);
        if (password == null || password.chars().allMatch(Character::isLetterOrDigit)) {
            errors.add("password perlu simbol");
        }
    }

    private static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    private static LocalDate parseDate(String value) {
        if (value == null) {
            return null;
        }
        try {
            return LocalDate.parse(value, STRICT_DATE);
        } catch (DateTimeException ignored) {
            return null;
        }
    }

    private static ValidationResult finish(List<String> errors, String successMessage) {
        if (errors.isEmpty()) {
            return ValidationResult.accepted(successMessage);
        }
        return ValidationResult.rejected("Ditolak: " + String.join("; ", errors));
    }
}
