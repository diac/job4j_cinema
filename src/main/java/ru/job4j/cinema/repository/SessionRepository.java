package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Session
 * @see ru.job4j.cinema.model.Session
 */
public interface SessionRepository {

    /**
     * Получить все доступные объекты Session
     *
     * @return Список сеансов. Пустой список, если ничего не найдено
     */
    List<Session> findAll();

    /**
     * Получить один объект Session по id
     *
     * @param id Уникальный идентификатор объекта Session
     * @return Optional для объекта Session, если таковой существует в хранилище для переданного id. Иначе -- Optional.empty()
     */
    Optional<Session> findById(int id);

    /**
     * Добавить в хранилище новый объект Session
     *
     * @param session Объект Session, который необходимо добавить в хранилище
     * @return Optional объекта Session, соответствующего добавленному объекту.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    Optional<Session> add(Session session);

    /**
     * Обновить в хранилище объект Session
     *
     * @param session Объект Session, который необходимо обновить в хранилище
     * @return true в случае успешного обновления. Иначе -- false
     */
    boolean update(Session session);

    /**
     * Удалить из хранилища объект Session
     *
     * @param session Объект Session, который необходимо удалить из хранилища
     * @return true в случае успешного удаления. Иначе -- false
     */
    boolean delete(Session session);
}