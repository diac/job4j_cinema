package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cinema.config.DataSourceConfig;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {DataSourceConfig.class, TicketDBRepository.class, UserDBRepository.class, SessionDBRepository.class})
public class TicketDBRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    public void whenCreateTicket() {
        String name = String.valueOf(System.currentTimeMillis());
        User user = userRepository.add(new User(0, name, name, name, name)).orElse(null);
        Session session = sessionRepository.add(new Session(0, name)).orElse(null);
        Ticket ticket = new Ticket(
                0,
                session.getId(),
                1,
                1,
                user.getId()
        );
        ticketRepository.add(ticket);
        Ticket ticketIndDb = ticketRepository.findById(ticket.getId())
                .orElse(new Ticket(0, 0, 0, 0, 0));
        assertThat(ticketIndDb.getPosRow()).isEqualTo(ticket.getPosRow());
        assertThat(ticketIndDb.getCell()).isEqualTo(ticket.getCell());
        assertThat(ticketIndDb.getUserId()).isEqualTo(ticket.getUserId());
        assertThat(ticketIndDb.getSessionId()).isEqualTo(ticket.getSessionId());
    }

    @Test
    public void whenCreateDuplicateTicketThenAddResultIsEmpty() {
        String name = String.valueOf(System.currentTimeMillis());
        User user = userRepository.add(new User(0, name, name, name, name)).orElse(null);
        Session session = sessionRepository.add(new Session(0, name)).orElse(null);
        Ticket ticket = new Ticket(
                0,
                session.getId(),
                1,
                1,
                user.getId()
        );
        ticketRepository.add(ticket);
        Ticket duplicateTicket = new Ticket(
                0,
                ticket.getSessionId(),
                ticket.getPosRow(),
                ticket.getCell(),
                ticket.getUserId()
        );
        assertThat(ticketRepository.add(duplicateTicket)).isEmpty();
    }

    @Test
    public void whenUpdateTicket() {
        String name = String.valueOf(System.currentTimeMillis());
        User user = userRepository.add(new User(0, name, name, name, name)).orElse(null);
        Session session = sessionRepository.add(new Session(0, name)).orElse(null);
        Ticket ticket = new Ticket(
                0,
                session.getId(),
                1,
                1,
                user.getId()
        );
        ticketRepository.add(ticket);
        ticket.setPosRow(5);
        ticket.setCell(5);
        ticketRepository.update(ticket);
        Ticket ticketIndDb = ticketRepository.findById(ticket.getId())
                .orElse(new Ticket(0, 0, 0, 0, 0));
        assertThat(ticketIndDb.getPosRow()).isEqualTo(ticket.getPosRow());
        assertThat(ticketIndDb.getCell()).isEqualTo(ticket.getCell());
        assertThat(ticketIndDb.getUserId()).isEqualTo(ticket.getUserId());
        assertThat(ticketIndDb.getSessionId()).isEqualTo(ticket.getSessionId());
    }

    @Test
    public void whenDeleteTicket() {
        String name = String.valueOf(System.currentTimeMillis());
        Session session = sessionRepository.add(new Session(0, name)).orElse(null);
        User user = userRepository.add(new User(0, name, name, name, name)).orElse(null);
        Ticket ticket = new Ticket(
                0,
                session.getId(),
                0,
                0,
                user.getId()
        );
        ticketRepository.add(ticket);
        int ticketId = ticket.getId();
        ticketRepository.delete(ticket);
        assertThat(ticketRepository.findById(ticketId)).isEmpty();
    }
}