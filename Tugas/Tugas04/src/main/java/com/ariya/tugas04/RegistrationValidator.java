package com.ariya.tugas04;

public class RegistrationValidator {
    public String validasiPendaftaran(
            String nama, int umur, String email, boolean setujuSyarat) {
        if (nama == null || nama.isEmpty()) {
            return "Nama harus diisi";
        }
        if (umur < 17) {
            return "Umur minimal 17 tahun";
        }
        if (email == null || !email.contains("@")) {
            return "Email tidak valid";
        }
        if (!setujuSyarat) {
            return "Harus menyetujui syarat dan ketentuan";
        }
        return "Pendaftaran berhasil";
    }
}
