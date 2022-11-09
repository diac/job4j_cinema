package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.util.List;

public interface SessionRepository {

    List<Session> findAll();

    Session findById(int id);

    Session add(Session session);

    Session update(Session session);

    boolean delete(Session session);
}