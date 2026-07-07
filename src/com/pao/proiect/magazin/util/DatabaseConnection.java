package com.pao.proiect.magazin.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        conecteaza();
    }

    private void conecteaza() {
        try {
            Properties props = new Properties();
            try (FileInputStream in = new FileInputStream("resources/db.properties")) {
                props.load(in);
            }
            Class.forName(props.getProperty("db.driver"));
            connection = DriverManager.getConnection(props.getProperty("db.url"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Eroare la conectarea la baza de date: " + e.getMessage(), e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) instance = new DatabaseConnection();
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) conecteaza();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void executeScript(String path) {
        try {
            String continut = new String(Files.readAllBytes(Path.of(path)));
            String[] instructiuni = continut.split(";");
            try (Statement st = getConnection().createStatement()) {
                for (String instr : instructiuni) {
                    String trimmed = instr.trim();
                    if (!trimmed.isEmpty()) st.execute(trimmed);
                }
            }
            System.out.println("[DatabaseConnection] Schema initializata din " + path);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Eroare la rularea scriptului " + path + ": " + e.getMessage(), e);
        }
    }
}