package com.pao.laboratory04.bonus;

import java.util.*;

public class TaskService {
    private static TaskService instance;
    private final Map<String, Task> tasksById = new HashMap<>();
    private final Map<Priority, List<Task>> tasksByPriority = new EnumMap<>(Priority.class);
    private final List<String> auditLog = new ArrayList<>();
    private int nextId = 1;

    private TaskService() {}

    public static TaskService getInstance() {
        if (instance == null) instance = new TaskService();
        return instance;
    }

    public void addTask(String title, Priority priority, String assignee) {
        String id = String.format("T%03d", nextId++);
        Task task = new Task(id, title, priority, assignee);

        tasksById.put(id, task);
        tasksByPriority.computeIfAbsent(priority, k -> new ArrayList<>()).add(task);

        auditLog.add("[ADD] " + id + ": '" + title + "' (" + priority + ")");
    }

    public void updateStatus(String id, Status newStatus) {
        Task task = tasksById.get(id);
        if (task == null) throw new TaskNotFoundException("Task-ul " + id + " nu există!");

        if (!task.getStatus().canTransitionTo(newStatus)) {
            throw new InvalidTransitionException(task.getStatus(), newStatus);
        }

        Status oldStatus = task.getStatus();
        task.setStatus(newStatus);
        auditLog.add("[UPDATE] " + id + " status: " + oldStatus + " -> " + newStatus);
    }

    public void printAuditLog() {
        System.out.println("=== Audit Log ===");
        auditLog.forEach(System.out::println);
    }

    public void printAllTasks() {
        tasksById.values().forEach(System.out::println);
    }
}