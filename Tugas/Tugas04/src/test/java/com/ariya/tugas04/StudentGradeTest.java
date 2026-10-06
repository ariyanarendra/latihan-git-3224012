package com.ariya.tugas04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StudentGradeTest {
    private final StudentGrade studentGrade = new StudentGrade();

    @Test
    void calculateGradeMengembalikanSemuaKategori() {
        assertEquals("A", studentGrade.calculateGrade(80));
        assertEquals("B", studentGrade.calculateGrade(70));
        assertEquals("C", studentGrade.calculateGrade(60));
        assertEquals("D", studentGrade.calculateGrade(50));
        assertEquals("E", studentGrade.calculateGrade(49));
    }

    @Test
    void calculateGradeMenerimaBatasAtas() {
        assertEquals("A", studentGrade.calculateGrade(100));
    }

    @Test
    void calculateGradeMenolakNilaiDiLuarRentang() {
        assertThrows(IllegalArgumentException.class, () -> studentGrade.calculateGrade(-1));
        assertThrows(IllegalArgumentException.class, () -> studentGrade.calculateGrade(101));
    }

    @Test
    void isPassedMenggunakanBatasEnamPuluh() {
        assertFalse(studentGrade.isPassed(59));
        assertTrue(studentGrade.isPassed(60));
        assertTrue(studentGrade.isPassed(100));
    }

    @Test
    void isPassedMenolakNilaiDiLuarRentang() {
        assertThrows(IllegalArgumentException.class, () -> studentGrade.isPassed(-1));
        assertThrows(IllegalArgumentException.class, () -> studentGrade.isPassed(101));
    }
}
