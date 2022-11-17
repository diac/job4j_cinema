package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.Assertions.assertThat;

public class TicketDBRepositoryTest {

    @Test
    public void whenCreateTicket() {
        TicketRepository ticketRepository = new TicketDBRepository(new Main().loadPool());
        UserRepository userRepository = new UserDBRepository(new Main().loadPool());
        SessionRepository sessionRepository = new SessionDBRepository(new Main().loadPool());
        String name = String.valueOf(System.currentTimeMillis());
        User user = userRepository.add(new User(0, name, name, name, name)).orElse(null);
        Session session = sessionRepository.add(new Session(0, name)).orElse(null);
        Ticket ticket = new Ticket(
                0,
                session,
                1,
                1,
                user
        );
        ticketRepository.add(ticket);
        Ticket ticketIndDb = ticketRepository.findById(ticket.getId())
                .orElse(new Ticket(0, null, 0, 0, null));
        assertThat(ticketIndDb.getPosRow()).isEqualTo(ticket.getPosRow());
        assertThat(ticketIndDb.getCell()).isEqualTo(ticket.getCell());
        assertThat(ticketIndDb.getUser()).isEqualTo(ticket.getUser());
        assertThat(ticketIndDb.getSession()).isEqualTo(ticket.getSession());
    }

    @Test
    public void whenCreateDuplicateTicketThenAddResultIsEmpty() {
        TicketRepository ticketRepository = new TicketDBRepository(new Main().loadPool());
        UserRepository userRepository = new UserDBRepository(new Main().loadPool());
        SessionRepository sessionRepository = new SessionDBRepository(new Main().loadPool());
        String name = String.valueOf(System.currentTimeMillis());
        User user = userRepository.add(new User(0, name, name, name, name)).orElse(null);
        Session session = sessionRepository.add(new Session(0, name)).orElse(null);
        Ticket ticket = new Ticket(
                0,
                session,
                1,
                1,
                user
        );
        ticketRepository.add(ticket);
        Ticket duplicateTicket = new Ticket(
                0,
                ticket.getSession(),
                ticket.getPosRow(),
                ticket.getCell(),
                ticket.getUser()
        );
        assertThat(ticketRepository.add(duplicateTicket)).isEmpty();
    }

    @Test
    public void whenUpdateTicket() {
        TicketRepository ticketRepository = new TicketDBRepository(new Main().loadPool());
        UserRepository userRepository = new UserDBRepository(new Main().loadPool());
        SessionRepository sessionRepository = new SessionDBRepository(new Main().loadPool());
        String name = String.valueOf(System.currentTimeMillis());
        User user = userRepository.add(new User(0, name, name, name, name)).orElse(null);
        Session session = sessionRepository.add(new Session(0, name)).orElse(null);
        Ticket ticket = new Ticket(
                0,
                session,
                1,
                1,
                user
        );
        ticketRepository.add(ticket);
        ticket.setPosRow(5);
        ticket.setCell(5);
        ticketRepository.update(ticket);
        Ticket ticketIndDb = ticketRepository.findById(ticket.getId())
                .orElse(new Ticket(0, null, 0, 0, null));
        assertThat(ticketIndDb.getPosRow()).isEqualTo(ticket.getPosRow());
        assertThat(ticketIndDb.getCell()).isEqualTo(ticket.getCell());
        assertThat(ticketIndDb.getUser()).isEqualTo(ticket.getUser());
        assertThat(ticketIndDb.getSession()).isEqualTo(ticket.getSession());
    }

    @Test
    public void whenDeleteTicket() {
        TicketRepository ticketRepository = new TicketDBRepository(new Main().loadPool());
        SessionRepository sessionRepository = new SessionDBRepository(new Main().loadPool());
        UserRepository userRepository = new UserDBRepository(new Main().loadPool());
        String name = String.valueOf(System.currentTimeMillis());
        Session session = sessionRepository.add(new Session(0, name)).orElse(null);
        User user = userRepository.add(new User(0, name, name, name, name)).orElse(null);
        Ticket ticket = new Ticket(
                0,
                session,
                0,
                0,
                user
        );
        ticketRepository.add(ticket);
        int ticketId = ticket.getId();
        ticketRepository.delete(ticket);
        assertThat(ticketRepository.findById(ticketId)).isEmpty();
    }
}