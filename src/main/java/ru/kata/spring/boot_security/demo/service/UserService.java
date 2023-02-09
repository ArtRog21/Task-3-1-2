package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    void addUser(User user);
    void deleteUser(int id);
    User editUser(int id, User user);
    User getById(int id);
    User getUserByName(String username);
}
