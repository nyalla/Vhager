package com.spaces.driftmark.concurrency;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.concurrent.BlockingQueue;

public class WriterConsumer implements Runnable {
    private BlockingQueue<QueueWorker> queue;
    private ThreadLocal<Connection> threadLocalValue;

    public WriterConsumer(BlockingQueue<QueueWorker> queue) {
        this.queue = queue;
        threadLocalValue = ThreadLocal.withInitial(() -> assignConnection());
        System.out.println("DB Connection for " + Thread.currentThread().getName() + "   " + threadLocalValue.get().toString());
    }

    @Override
    public void run() {
        try {
            QueueWorker worker;
            while (!(worker = queue.take()).isPOISON_PILL() && worker.getItemsHolder().length > 0) {
                //System.out.println("DB Connection FROM WORK " + Thread.currentThread().getName() + "   " + threadLocalValue.get().toString());
                worker.insert(threadLocalValue.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            try {
                threadLocalValue.get().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("END OF " + Thread.currentThread().getName() + "at time: " + Instant.now());
        }
    }

    private Connection assignConnection() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "password";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }
}
