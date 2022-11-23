package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    List<Ticket> findAll();

    Optional<Ticket> findById(int id);

    Optional<Ticket> add(Ticket ticket);

    boolean update(Ticket ticket);

    List<Ticket> findAllBySessionId(int sessionId);

    boolean delete(Ticket ticket);
}