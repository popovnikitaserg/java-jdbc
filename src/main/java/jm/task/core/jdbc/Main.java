package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("nikita", "popov", (byte)20);
        userService.saveUser("mikhail", "pupkin", (byte)21);
        userService.saveUser("vasya", "pupkin", (byte)27);
        userService.saveUser("andrey", "mikhalkov", (byte)78);

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
