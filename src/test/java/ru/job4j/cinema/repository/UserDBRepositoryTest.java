package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDBRepositoryTest {

    @Test
    public void whenCreateUser() {
        UserRepository repository = new UserDBRepository(new Main().loadPool());
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
                username,
                username + "@test.text",
                username

        );
        repository.add(user);
        User userInDb = repository.findById(user.getId()).orElse(new User(0, null, null, null));
        assertThat(userInDb.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenUpdateUser() {
        UserRepository repository = new UserDBRepository(new Main().loadPool());
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
                username,
                username + "@test.text",
                username

        );
        repository.add(user);
        user.setUsername(user.getUsername() + "_updated");
        user.setEmail(user.getEmail() + "_updated");
        user.setPhone(user.getPhone() + "_updated");
        repository.update(user);
        User userInDb = repository.findById(user.getId()).orElse(new User(0, null, null, null));
        assertThat(userInDb.getUsername()).isEqualTo(user.getUsername());
        assertThat(userInDb.getEmail()).isEqualTo(user.getEmail());
        assertThat(userInDb.getPhone()).isEqualTo(user.getPhone());
    }

    @Test
    public void whenDeleteUser() {
        UserRepository repository = new UserDBRepository(new Main().loadPool());
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
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