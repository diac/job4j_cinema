package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Session
 *
 * @see ru.job4j.cinema.model.Session
 */
public interface SessionService {

    /**
     * Получить все объекты для модели Session
     *
     * @return Список сеансов. Пустой список, если ничего не найдено
     */
    List<Session> findAll();

    /**
     * Получить один объект Session по id
     *
     * @param id Уникальный идентификатор объекта Session
     * @return Optional для объекта Session, если существует объект для переданного id.
     * Иначе -- Optional.empty()
     */
    Optional<Session> findById(int id);

    /**
     * Добавить в хранилище новый объект из объекта Session
     *
     * @param session Объект Session, который нужно добавить в хранилище
     * @return Optional для объекта Session, если удалось добавить этот объект в хранилище. Иначе -- Optional.empty()
     */
    Optional<Session> add(Session session);

    /**
     * Обновить в хранилище объект, соответствующий передаваемому объекту Session
     *
     * @param session Объект Session, который нужно обновить в хранилище
     * @return true в случае успешного обновления. Иначе -- false
     */
    boolean update(Session session);

    /**
     * Удалить из хранилища объект, соответствующий передаваемому объекту Session
     *
     * @param session Объект Session, который необходимо удалить из хранилища
     * @return true в случае успешного удаления. Иначе -- false
     */
    boolean delete(Session session);
}