package com.fedelesh.flowersalon.infrastructure.storage.util;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static final String URL = "jdbc:h2:~/flowersalondb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final int POOL_SIZE = 5;

    private static ConnectionPool instance;
    private final BlockingQueue<Connection> pool;

    private ConnectionPool() {
        this.pool = new ArrayBlockingQueue<>(POOL_SIZE);
        init();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void init() {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.offer(createProxyConnection());
        }
    }

    private Connection createProxyConnection() {
        try {
            Connection realConnection = DriverManager.getConnection(URL, USER, PASSWORD);

            return (Connection) Proxy.newProxyInstance(
                  ConnectionPool.class.getClassLoader(),
                  new Class[]{Connection.class},
                  (proxy, method, args) -> {

                      if ("close".equals(method.getName())) {
                          try {
                              realConnection.setAutoCommit(true);
                              realConnection.setTransactionIsolation(
                                    Connection.TRANSACTION_READ_COMMITTED
                              );
                          } catch (SQLException ignored) {
                              
                          }

                          pool.offer((Connection) proxy);
                          return null;
                      }

                      return method.invoke(realConnection, args);
                  }
            );

        } catch (SQLException e) {
            throw new RuntimeException("Cannot create connection", e);
        }
    }

    public Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("No connection available", e);
        }
    }
}