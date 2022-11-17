package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> add(User user);

    boolean update(User user);

    boolean delete(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}