package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionDBRepositoryTest {

    @Test
    public void whenCreateSession() {
        SessionRepository repository = new SessionDBRepository(new Main().loadPool());
        String sessionName = String.valueOf(System.currentTimeMillis());
        Session session = new Session(0, sessionName);
        repository.add(session);
        Session sessionInDb = repository.findById(session.getId()).orElse(new Session(0, null));
        assertThat(sessionInDb.getName()).isEqualTo(session.getName());
    }

    @Test
    public void whenUpdateSession() {
        SessionRepository repository = new SessionDBRepository(new Main().loadPool());
        String sessionName = String.valueOf(System.currentTimeMillis());
        Session session = new Session(0, sessionName);
        repository.add(session);
        session.setName(session.getName() + "_updated");
        repository.update(session);
        Session sessionInDb = repository.findById(session.getId()).orElse(new Session(0, null));
        assertThat(sessionInDb.getName()).isEqualTo(session.getName());
    }

    @Test
    public void whenDeleteSession() {
        SessionRepository repository = new SessionDBRepository(new Main().loadPool());
        String sessionName = String.valueOf(System.currentTimeMillis());
        Session session = new Session(0, sessionName);
        repository.add(session);
        int sessionId = session.getId();
        repository.delete(session);
        assertThat(repository.findById(sessionId)).isEmpty();
    }
}