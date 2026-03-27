package com.pao.laboratory04.enums;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Toate prioritățile ===");
        for (Priority p : Priority.values()) {
            String emoji = switch (p) {
                case LOW -> "🟢 ";
                case MEDIUM -> "🟡 ";
                case HIGH -> "🟠 ";
                case CRITICAL -> "🔴 ";
            };
            System.out.println(emoji + p);
        }

        System.out.println("\n=== Switch pe prioritate ===");
        Priority current = Priority.HIGH;
        switch (current) {
            case HIGH, CRITICAL -> System.out.println("⚠️ Atenție! Prioritate ridicată!");
            default -> System.out.println("Totul este sub control.");
        }

        System.out.println("\n=== valueOf ===");
        Priority high = Priority.valueOf("HIGH");
        System.out.println("Priority.valueOf(\"HIGH\") = " + high);

        System.out.println("\n=== Comparare enum ===");
        System.out.println("HIGH == HIGH? " + (high == Priority.HIGH));
        System.out.println("HIGH == LOW? " + (high == Priority.LOW));

        System.out.println("\n=== name() și ordinal() ===");
        for (Priority p : Priority.values()) {
            System.out.println(p.name() + ": name=" + p.name() + ", ordinal=" + p.ordinal());
        }
    }
}