package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

public interface SessionService {

    List<Session> findAll();

    Optional<Session> findById(int id);

    Optional<Session> add(Session session);

    boolean update(Session session);

    boolean delete(Session session);
}