package com.spaces.driftmark.service;

import com.spaces.driftmark.concurrency.QueueModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatapusherCon {

    public void insert(QueueModel[] itemsHolder) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "password";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO person (pid, pname) " +
                    "VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 0; i < itemsHolder.length; i++) {
                statement.setString(1, itemsHolder[i].getPersonId());
                statement.setString(2, itemsHolder[i].getPersonDesc());
                statement.addBatch();
            }
            statement.executeBatch();

            connection.commit();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertNew(QueueModel[] itemsHolder, Connection connection) {
        try {

            final String sql = "INSERT INTO person (pid, pname) " +
                    "VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            for (int i = 0; i < itemsHolder.length; i++) {
                statement.setString(1, itemsHolder[i].getPersonId());
                statement.setString(2, itemsHolder[i].getPersonDesc());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
