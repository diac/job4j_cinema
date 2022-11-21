package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Main.class)
public class UserDBRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void whenCreateUser() {
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
                username,
                username,
                username + "@test.text",
                username

        );
        repository.add(user);
        User userInDb = repository.findById(user.getId())
                .orElse(new User(0, null, null, null, null));
        assertThat(userInDb.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenUpdateUser() {
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
                username,
                username,
                username + "@test.text",
                username

        );
        repository.add(user);
        user.setUsername(user.getUsername() + "_updated");
        user.setPassword(user.getPassword() + "_updated");
        user.setEmail(user.getEmail() + "_updated");
        user.setPhone(user.getPhone() + "_updated");
        repository.update(user);
        User userInDb = repository.findById(user.getId())
                .orElse(new User(0, null, null, null, null));
        assertThat(userInDb.getUsername()).isEqualTo(user.getUsername());
        assertThat(userInDb.getPassword()).isEqualTo(user.getPassword());
        assertThat(userInDb.getEmail()).isEqualTo(user.getEmail());
        assertThat(userInDb.getPhone()).isEqualTo(user.getPhone());
    }

    @Test
    public void whenDeleteUser() {
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
                username,
                username,
                username + "@test.text",
                username

        );
        repository.add(user);
        int userId = user.getId();
        repository.delete(user);
        assertThat(repository.findById(userId)).isEmpty();
    }
}