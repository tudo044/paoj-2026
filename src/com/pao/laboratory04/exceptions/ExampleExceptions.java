package com.pao.laboratory04.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Exemplu demonstrativ — Excepții în Java.
 * Rulează acest main pentru a vedea try-catch-finally, custom exceptions, multi-catch.
 * Apoi rezolvă exercițiul din Main.java (creezi InvalidAgeException + DuplicateEntryException).
 */
public class ExampleExceptions {

    private static class InsufficientFundsException extends RuntimeException {
        private final double balance;
        private final double amount;

        public InsufficientFundsException(double balance, double amount) {
            super("Sold insuficient: ai " + balance + " lei, dar vrei să retragi " + amount + " lei");
            this.balance = balance;
            this.amount = amount;
        }

        public double getBalance() { return balance; }
        public double getAmount() { return amount; }
    }

    private static class NegativeAmountException extends RuntimeException {
        public NegativeAmountException(double amount) {
            super("Suma nu poate fi negativă: " + amount);
        }
    }

    private static double withdraw(double balance, double amount) {
        if (amount < 0) {
            throw new NegativeAmountException(amount);
        }
        if (amount > balance) {
            throw new InsufficientFundsException(balance, amount);
        }
        return balance - amount;
    }

    public static void main(String[] args) {

        System.out.println("=== 1. try-catch simplu ===");
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Prins: " + e.getMessage());
        }

        System.out.println("\n=== 2. try-catch-finally ===");
        try {
            String text = null;
            text.toUpperCase();
        } catch (NullPointerException e) {
            System.out.println("Prins: " + e.getMessage());
        } finally {
            System.out.println("Finally — se execută MEREU, chiar și cu return!");
        }

        System.out.println("\n=== 3. Excepții custom ===");
        try {
            double result = withdraw(100.0, 250.0);
            System.out.println("Sold nou: " + result);
        } catch (InsufficientFundsException e) {
            System.out.println("InsufficientFundsException: " + e.getMessage());
            System.out.println("  Sold curent: " + e.getBalance());
            System.out.println("  Sumă cerută: " + e.getAmount());
        }

        System.out.println("\n=== 4. Multi-catch ===");
        try {
            withdraw(500, -20);
        } catch (InsufficientFundsException | NegativeAmountException e) {
            System.out.println("Eroare tranzacție: " + e.getMessage());
        }

        System.out.println("\n=== 5. Catch ordering ===");
        try {
            withdraw(50, 100);
        } catch (InsufficientFundsException e) {
            System.out.println("Specific: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("General: " + e.getMessage());
        }

        System.out.println("\n=== 6. Prindere și re-aruncare ===");
        try {
            try {
                withdraw(10, 100);
            } catch (InsufficientFundsException e) {
                System.out.println("Logare eroare: " + e.getMessage());
                throw e;
            }
        } catch (RuntimeException e) {
            System.out.println("Prinsă la nivel superior: " + e.getClass().getSimpleName());
        }

        System.out.println("\n=== 7. Excepții în colecții ===");
        List<String> emails = new ArrayList<>();
        String[] input = {"ana@mail.com", "invalid-email", "dan@mail.com", "ana@mail.com"};

        for (String email : input) {
            try {
                if (!email.contains("@")) {
                    throw new IllegalArgumentException("Email invalid: " + email);
                }
                if (emails.contains(email)) {
                    throw new RuntimeException("Email duplicat: " + email);
                }
                emails.add(email);
                System.out.println("  ✓ Adăugat: " + email);
            } catch (RuntimeException e) {
                System.out.println("  ✗ " + e.getMessage());
            }
        }
        System.out.println("Emails valide: " + emails);
    }
}