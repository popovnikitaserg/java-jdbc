package jm.task.core.jdbc.util;

import java.rmi.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "nikita";
        String password = "nikita";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
