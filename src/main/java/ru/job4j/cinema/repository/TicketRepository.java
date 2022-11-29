package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Ticket
 * @see ru.job4j.cinema.model.Ticket
 */
public interface TicketRepository {

    /**
     * Получить все доступные объекты Ticket
     *
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    List<Ticket> findAll();

    /**
     * Получить один объект Ticket по id
     *
     * @param id Уникальный идентификатор объекта Ticket
     * @return Optional для объекта Ticket, если таковой существует в хранилище для переданного id.
     * Иначе -- Optional.empty()
     */
    Optional<Ticket> findById(int id);

    /**
     * Добавить в хранилище новый объект Ticket
     *
     * @param ticket Объект Ticket, который необходимо добавить в хранилище
     * @return Optional объекта Ticket, соответствующего добавленному объекту.
     * Optional.empty() в случае, если новую запись не удалось создать
     */
    Optional<Ticket> add(Ticket ticket);

    /**
     * Обновить в хранилище объект Ticket
     *
     * @param ticket Объект Ticket, который необходимо обновить в хранилище
     * @return true в случае успешного обновления. Иначе -- false
     */
    boolean update(Ticket ticket);

    /**
     * Удалить из хранилища объект Ticket
     *
     * @param ticket Объект Ticket, который необходимо удалить из хранилища
     * @return true в случае успешного удаления. Иначе -- false
     */
    boolean delete(Ticket ticket);

    /**
     * Получить все объекты модели Ticket из для конкретного сеанса
     *
     * @param sessionId ID сеанса
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    List<Ticket> findAllBySessionId(int sessionId);
}