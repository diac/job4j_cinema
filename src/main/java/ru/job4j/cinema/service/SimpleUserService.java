package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели User в репозитории
 */
@Service
public class SimpleUserService implements UserService {

    private final UserRepository repository;

    /**
     * Конструктор для сервиса
     *
     * @param repository Объект-репозиторий
     */
    public SimpleUserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Получить все объекты для модели User из репозитория
     *
     * @return Список пользователей. Пустой список, если ничего не найдено
     */
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * Получить один объект User из репозитория по id
     *
     * @param id Уникальный идентификатор объекта User
     * @return Optional для объекта User, если в репозитории существует объект для переданного id.
     * Иначе -- Optional.empty()
     */
    public Optional<User> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Добавить новый объект в репозиторий из объекта User
     *
     * @param user Объект User, который нужно добавить в репозиторий
     * @return Optional для объекта User, если удалось добавить этот объект в репозиторий. Иначе -- Optional.empty()
     */
    public Optional<User> add(User user) {
        return repository.add(user);
    }

    /**
     * Обновить в репозитории объект, соответствующий передаваемому объекту User
     *
     * @param user Объект User, который нужно обновить в репозитории
     * @return true в случае успешного обновления. Иначе -- false
     */
    public boolean update(User user) {
        return repository.update(user);
    }

    /**
     * Удалить из репозитория объект, соответствующий передаваемому объекту User
     *
     * @param user Объект User, который необходимо удалить из репозитория
     * @return true в случае успешного удаления. Иначе -- false
     */
    public boolean delete(User user) {
        return repository.delete(user);
    }

    /**
     * Найти пользователя по имени пользователя
     *
     * @param username Имя пользователя
     * @return Optional объекта User. Optional.empty(), если пользователь не найден
     */
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    /**
     * Найти пользователя по имени пользователя и паролю
     *
     * @param username Имя пользователя
     * @return Optional объекта User. Optional.empty(), если пользователь не найден
     */
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }
}