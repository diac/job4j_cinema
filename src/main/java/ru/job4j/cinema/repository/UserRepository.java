package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(int id);

    User add(User user);

    User update(User user);

    boolean delete(User user);
}