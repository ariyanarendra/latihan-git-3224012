package com.ariya.tugas04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RegistrationValidatorTest {
    private final RegistrationValidator validator = new RegistrationValidator();

    @Test
    void namaWajibDiisi() {
        assertEquals(
                "Nama harus diisi",
                validator.validasiPendaftaran(null, 20, "ari@example.com", true));
    }

    @Test
    void namaKosongDitolak() {
        assertEquals(
                "Nama harus diisi",
                validator.validasiPendaftaran("", 20, "ari@example.com", true));
    }

    @Test
    void umurMinimalTujuhBelasTahun() {
        assertEquals(
                "Umur minimal 17 tahun",
                validator.validasiPendaftaran("Ari", 16, "ari@example.com", true));
    }

    @Test
    void emailTidakBolehNull() {
        assertEquals(
                "Email tidak valid",
                validator.validasiPendaftaran("Ari", 17, null, true));
    }

    @Test
    void emailHarusMemilikiTandaAt() {
        assertEquals(
                "Email tidak valid",
                validator.validasiPendaftaran("Ari", 17, "ari.example.com", true));
    }

    @Test
    void syaratHarusDisetujui() {
        assertEquals(
                "Harus menyetujui syarat dan ketentuan",
                validator.validasiPendaftaran("Ari", 17, "ari@example.com", false));
    }

    @Test
    void pendaftaranBerhasilUntukDataValid() {
        assertEquals(
                "Pendaftaran berhasil",
                validator.validasiPendaftaran("Ari", 17, "ari@example.com", true));
    }
}
