package com.ariya.tugas04;

public class BankAccount {
    private double balance;

    public BankAccount() {
        this(0.0);
    }

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        balance = initialBalance;
    }

    public void deposit(double amount) {
        validateAmount(amount);
        balance += amount;
    }

    public void withdraw(double amount) {
        validateAmount(amount);
        if (amount > balance) {
            throw new IllegalArgumentException("Withdrawal cannot exceed balance");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    private void validateAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }
}
