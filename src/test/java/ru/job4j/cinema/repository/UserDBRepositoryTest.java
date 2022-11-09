package ru.job4j.cinema.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.User;

import static org.assertj.core.api.Assertions.*;

@Disabled
public class UserDBRepositoryTest {

    @Test
    public void whenCreateUser() {
        UserRepository repository = new UserDBRepository();
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
                username,
                username + "@test.text",
                "1111111111"

        );
        repository.add(user);
        User userInDb = repository.findById(user.getId());
        assertThat(userInDb.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenUpdateUser() {
        UserRepository repository = new UserDBRepository();
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
                username,
                username + "@test.text",
                "1111111111"

        );
        repository.add(user);
        String updatedUsername = username + "_updated";
        user.setUsername(updatedUsername);
        User userInDb = repository.findById(user.getId());
        assertThat(userInDb.getUsername()).isEqualTo(updatedUsername);
    }

    @Test
    public void whenDeleteUser() {
        UserRepository repository = new UserDBRepository();
        String username = String.valueOf(System.currentTimeMillis());
        User user = new User(
                0,
                username,
                username + "@test.text",
                "1111111111"

        );
        repository.add(user);
        int userId = user.getId();
        repository.delete(user);
        assertThat(repository.findById(userId)).isNull();
    }
}