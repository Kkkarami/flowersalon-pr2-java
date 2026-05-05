package com.fedelesh.flowersalon.infrastructure.storage.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseInitializer {

    public static void init() {
        if (isInitialized()) {
            return;
        }

        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();

            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement()) {

                statement.execute(loadSql("db/schema.sql"));
                statement.execute(loadSql("db/data.sql"));
            }

            connection.commit();

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

    private static boolean isInitialized() {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
              Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(
                  "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'USERS'"
            );

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            throw new RuntimeException("DB check failed", e);
        }

        return false;
    }

    private static String loadSql(String path) {
        try (BufferedReader reader = new BufferedReader(
              new InputStreamReader(
                    Objects.requireNonNull(
                          DatabaseInitializer.class.getClassLoader()
                                .getResourceAsStream(path))))) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (Exception e) {
            throw new RuntimeException("SQL file not found: " + path, e);
        }
    }
}