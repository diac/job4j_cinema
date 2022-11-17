package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    List<Ticket> findAll();

    Optional<Ticket> findById(int id);

    Optional<Ticket> add(Ticket ticket);

    boolean update(Ticket ticket);

    boolean delete(Ticket ticket);

    List<Ticket> findAllBySessionId(int sessionId);
}