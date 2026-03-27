package com.pao.laboratory04.exercise;

import java.util.*;

public class StudentService {
    private static StudentService instance;
    private final List<Student> students = new ArrayList<>();

    private StudentService() {}

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException("Studentul '" + name + "' există deja!");
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new StudentNotFoundException("Studentul " + name + " nu a fost găsit!");
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        findByName(studentName).addGrade(subject, grade);
    }

    public void printAllStudents() {
        for (Student s : students) {
            System.out.println(s);
            s.getGrades().forEach((sub, g) -> System.out.println("   " + sub.name() + " = " + g));
        }
    }

    public void printTopStudents() {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));
        System.out.println("=== Top studenți ===");
        for (Student s : sorted) {
            System.out.printf("%s — media: %.2f%n", s.getName(), s.getAverage());
        }
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> sums = new EnumMap<>(Subject.class);
        Map<Subject, Integer> counts = new EnumMap<>(Subject.class);

        for (Student s : students) {
            s.getGrades().forEach((sub, g) -> {
                sums.put(sub, sums.getOrDefault(sub, 0.0) + g);
                counts.put(sub, counts.getOrDefault(sub, 0) + 1);
            });
        }

        Map<Subject, Double> averages = new EnumMap<>(Subject.class);
        sums.forEach((sub, sum) -> averages.put(sub, sum / counts.get(sub)));
        return averages;
    }
}