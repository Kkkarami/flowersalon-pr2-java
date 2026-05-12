package com.fedelesh.flowersalon.infrastructure.persistence.util;

import org.flywaydb.core.Flyway;

public class DatabaseInitializer {

    private static final String URL = "jdbc:h2:~/flowersalondb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void init() {

        Flyway flyway = Flyway.configure().dataSource(URL, USER, PASSWORD).load();

        flyway.migrate();

        System.out.println("Database migration completed");
    }
}
