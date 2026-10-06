package com.ariya.tugas04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BankAccountTest {
    @Test
    void saldoAwalDefaultAdalahNol() {
        assertEquals(0.0, new BankAccount().getBalance(), 0.001);
    }

    @Test
    void konstruktorMenerimaSaldoAwal() {
        assertEquals(250.0, new BankAccount(250.0).getBalance(), 0.001);
    }

    @Test
    void konstruktorMenolakSaldoAwalNegatif() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-1.0));
    }

    @Test
    void depositMenambahSaldo() {
        BankAccount account = new BankAccount();
        account.deposit(500.0);
        assertEquals(500.0, account.getBalance(), 0.001);
    }

    @Test
    void depositNegatifDitolak() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-10.0));
    }

    @Test
    void withdrawMengurangiSaldo() {
        BankAccount account = new BankAccount(500.0);
        account.withdraw(200.0);
        assertEquals(300.0, account.getBalance(), 0.001);
    }

    @Test
    void withdrawSeluruhSaldoMenghasilkanNol() {
        BankAccount account = new BankAccount(500.0);
        account.withdraw(500.0);
        assertEquals(0.0, account.getBalance(), 0.001);
    }

    @Test
    void withdrawNegatifDitolak() {
        BankAccount account = new BankAccount(500.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10.0));
    }

    @Test
    void withdrawMelebihiSaldoDitolak() {
        BankAccount account = new BankAccount(500.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(600.0));
    }
}
