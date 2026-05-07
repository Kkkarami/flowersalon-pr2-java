package com.fedelesh.flowersalon.infrastructure.storage.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    private static final String DB_FILE_NAME = "flowersalondb.mv.db";

    public static void init() {

        if (databaseExists()) {
            System.out.println("DB already exists - skipping init");
            return;
        }

        System.out.println("Creating new database...");

        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {

                executeSqlFile(statement, "db/schema.sql");
                executeSqlFile(statement, "db/data.sql");
            }

            connection.commit();

            System.out.println("DB initialized successfully");

        } catch (Exception e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception ignored) {
                }
            }

            throw new RuntimeException("DB init failed", e);

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private static boolean databaseExists() {

        String userHome = System.getProperty("user.home");
        Path dbPath = Paths.get(userHome, DB_FILE_NAME);

        return Files.exists(dbPath);
    }

    private static void executeSqlFile(Statement statement, String path) {

        String sql = loadSql(path);

        for (String query : sql.split(";")) {
            String trimmed = query.trim();

            if (!trimmed.isEmpty()) {
                try {
                    statement.execute(trimmed);
                } catch (Exception e) {
                    throw new RuntimeException(
                          "SQL execution error in file: " + path + "\nQuery: " + trimmed, e);
                }
            }
        }
    }

    private static String loadSql(String path) {

        try (BufferedReader reader = new BufferedReader(
              new InputStreamReader(
                    Objects.requireNonNull(
                          DatabaseInitializer.class.getClassLoader()
                                .getResourceAsStream(path)
                    )
              )
        )) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (Exception e) {
            throw new RuntimeException("SQL file not found: " + path, e);
        }
    }
}