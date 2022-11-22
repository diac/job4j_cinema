package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cinema.config.DataSourceConfig;
import ru.job4j.cinema.model.Session;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {DataSourceConfig.class, SessionDBRepository.class})
public class SessionDBRepositoryTest {

    @Autowired
    private SessionRepository repository;

    @Test
    public void whenCreateSession() {
        String sessionName = String.valueOf(System.currentTimeMillis());
        Session session = new Session(0, sessionName);
        repository.add(session);
        Session sessionInDb = repository.findById(session.getId()).orElse(new Session(0, null));
        assertThat(sessionInDb.getName()).isEqualTo(session.getName());
    }

    @Test
    public void whenUpdateSession() {
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
        String sessionName = String.valueOf(System.currentTimeMillis());
        Session session = new Session(0, sessionName);
        repository.add(session);
        int sessionId = session.getId();
        repository.delete(session);
        assertThat(repository.findById(sessionId)).isEmpty();
    }
}