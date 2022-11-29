package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели User
 * @see ru.job4j.cinema.model.User
 */
public interface UserRepository {

    /**
     * Получить все доступные объекты User
     *
     * @return Список пользователей. Пустой список, если ничего не найдено
     */
    List<User> findAll();

    /**
     * Получить один объект User по id
     *
     * @param id Уникальный идентификатор объекта User
     * @return Optional для объекта User, если таковой существует в хранилище для переданного id. Иначе -- Optional.empty()
     */
    Optional<User> findById(int id);

    /**
     * Добавить в хранилище новый объект User
     *
     * @param user Объект User, который необходимо добавить в хранилище
     * @return Optional объекта User, соответствующего добавленному объекту.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    Optional<User> add(User user);

    /**
     * Обновить в хранилище объект User
     *
     * @param user Объект User, который необходимо обновить в хранилище
     * @return true в случае успешного обновления. Иначе -- false
     */
    boolean update(User user);

    /**
     * Удалить из хранилища объект User
     *
     * @param user Объект User, который необходимо удалить из хранилища
     * @return true в случае успешного удаления. Иначе -- false
     */
    boolean delete(User user);

    /**
     * Получить один объект User по значению поля username
     *
     * @param username Имя пользователя в системе
     * @return Optional для объекта User, если таковой существует в хранилище для переданного username. Иначе -- Optional.empty()
     */
    Optional<User> findByUsername(String username);

    /**
     * Получить один объект User по значению полей username и password
     *
     * @param username Имя пользователя в системе
     * @param password Пароль пользователя в системе
     * @return Optional для объекта User, если таковой существует в хранилище для переданных username и password.
     * Иначе -- Optional.empty()
     */
    Optional<User> findByUsernameAndPassword(String username, String password);
}