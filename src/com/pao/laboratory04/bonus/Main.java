package com.pao.laboratory04.bonus;

public class Main {
    public static void main(String[] args) {
        TaskService service = TaskService.getInstance();

        System.out.println("=== Task Manager cu Audit ===");

        service.addTask("Implementare Modele", Priority.HIGH, "Andrei");
        service.addTask("Configurare DB", Priority.URGENT, "Elena");
        service.addTask("Documentație", Priority.LOW, "Mihai");

        try {
            service.updateStatus("T001", Status.IN_PROGRESS);
            service.updateStatus("T001", Status.DONE);
            // Tranziție invalidă: DONE -> TODO
            service.updateStatus("T001", Status.TODO);
        } catch (RuntimeException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        System.out.println("\n=== Listă Task-uri ===");
        service.printAllTasks();

        System.out.println();
        service.printAuditLog();
    }
}