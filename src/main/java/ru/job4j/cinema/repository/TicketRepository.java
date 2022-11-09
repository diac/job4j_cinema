package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.List;

public interface TicketRepository {

    List<Ticket> findAll();

    Ticket findById(int id);

    Ticket add(Ticket ticket);

    Ticket update(Ticket ticket);

    boolean delete(Ticket ticket);
}