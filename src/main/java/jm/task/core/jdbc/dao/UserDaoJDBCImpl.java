package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    static final Util util = new Util();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    lastName VARCHAR(255) NOT NULL,
                    age SMALLINT NOT NULL
                )
                """;
        try (Connection conn = util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String  sql = """
                DROP TABLE IF EXISTS users
        """;
        try (Connection connection = util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table dropped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)
        """;
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("Пользователь %s %s добавлен в базу%n", name, lastName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM users WHERE id = ?;
        """;

        try (Connection connection = util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.printf("Пользователь с id=%d удален%n", id);
            }  else {
                System.out.printf("Пользователь с id=%d не найден%n", id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
         List<User> users = new ArrayList<>();
         String sql = """
                SELECT id, name, lastName, age FROM users
         """;
         try (Connection connection = util.getConnection();
              Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery(sql)) {
             while (resultSet.next()) {
                 User user = new User(
                         resultSet.getString("name"),
                         resultSet.getString("lastName"),
                         resultSet.getByte("age")
                 );
                 user.setId(resultSet.getLong("id"));
                 users.add(user);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return users;
    }

    public void cleanUsersTable() {
        String sql = """
                TRUNCATE TABLE users
        """;

        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table cleaned");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
