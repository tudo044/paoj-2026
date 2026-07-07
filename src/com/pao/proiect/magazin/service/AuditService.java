package com.pao.proiect.magazin.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class AuditService {
    private static AuditService instance;
    private final ReentrantLock lock = new ReentrantLock();
    private static final String FISIER = "audit.csv";

    private AuditService() {
        initFisier();
    }

    public static synchronized AuditService getInstance() {
        if (instance == null) instance = new AuditService();
        return instance;
    }

    private void initFisier() {
        try {
            Path path = Path.of(FISIER);
            if (!Files.exists(path)) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(FISIER, true))) {
                    pw.println("nume_actiune,timestamp");
                }
            }
        } catch (IOException e) {
            System.err.println("Nu s-a putut initializa audit.csv: " + e.getMessage());
        }
    }

    public void log(String numeActiune) {
        lock.lock();
        try (PrintWriter pw = new PrintWriter(new FileWriter(FISIER, true))) {
            pw.println(numeActiune + "," + LocalDateTime.now());
        } catch (IOException e) {
            System.err.println("Eroare la scrierea in audit.csv: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}