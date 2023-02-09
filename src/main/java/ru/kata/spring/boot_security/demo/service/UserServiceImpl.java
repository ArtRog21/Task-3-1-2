package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> allUsers() {
        return userDao.findAll();
    }

    @Override
    public void addUser(User user) {
        if (user.getId() <= 0)
            userDao.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteById(id);
    }

    @Override
    public User editUser(int id, User user) {
        User editUser = userDao.getById(id);
        editUser.setUsername(user.getUsername());
        editUser.setLastname(user.getLastname());
        editUser.setEmail(user.getEmail());
        editUser.setPassword(user.getPassword());
        editUser.setRoles(user.getRoles());
        return userDao.save(editUser);
    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public User getUserByName(String username) {
        Optional<User> user = userDao.getUserByUsername(username);
        return user.get();
    }
}