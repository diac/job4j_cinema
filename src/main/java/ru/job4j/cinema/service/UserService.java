package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели User
 *
 * @see ru.job4j.cinema.model.User
 */
public interface UserService {

    /**
     * Получить все объекты для модели User из репозитория
     *
     * @return Список пользователей. Пустой список, если ничего не найдено
     */
    List<User> findAll();

    /**
     * Получить один объект User по id
     *
     * @param id Уникальный идентификатор объекта User
     * @return Optional для объекта User, если существует объект для переданного id.
     * Иначе -- Optional.empty()
     */
    Optional<User> findById(int id);

    /**
     * Добавить новый объект в хранилище из объекта User
     *
     * @param user Объект User, который нужно добавить в хранилище
     * @return Optional для объекта User, если удалось добавить этот объект в репозиторий. Иначе -- Optional.empty()
     */
    Optional<User> add(User user);

    /**
     * Обновить в хранилище объект, соответствующий передаваемому объекту User
     *
     * @param user Объект User, который нужно обновить в хранилище
     * @return true в случае успешного обновления. Иначе -- false
     */
    boolean update(User user);

    /**
     * Удалить из хранилища объект, соответствующий передаваемому объекту User
     *
     * @param user Объект User, который необходимо удалить из хранилища
     * @return true в случае успешного удаления. Иначе -- false
     */
    boolean delete(User user);

    /**
     * Найти пользователя по имени пользователя
     *
     * @param username Имя пользователя
     * @return Optional объекта User. Optional.empty(), если пользователь не найден
     */
    Optional<User> findByUsername(String username);

    /**
     * Найти пользователя по имени пользователя и паролю
     *
     * @param username Имя пользователя
     * @return Optional объекта User. Optional.empty(), если пользователь не найден
     */
    Optional<User> findByUsernameAndPassword(String username, String password);
}