package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Ticket.
 *
 * @see ru.job4j.cinema.model.Ticket
 */
public interface TicketService {

    /**
     * Получить все объекты для модели Ticket
     *
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    List<Ticket> findAll();

    /**
     * Получить один объект Ticket по id
     *
     * @param id Уникальный идентификатор объекта Ticket
     * @return Optional для объекта Ticket, если существует объект для переданного id.
     * Иначе -- Optional.empty()
     */
    Optional<Ticket> findById(int id);

    /**
     * Добавить в хранилище новый объект из объекта Ticket
     *
     * @param ticket Объект Ticket, который нужно добавить в хранилище
     * @return Optional для объекта Ticket, если удалось добавить этот объект в хранилище. Иначе -- Optional.empty()
     */
    Optional<Ticket> add(Ticket ticket);

    /**
     * Обновить в хранилище объект, соответствующий передаваемому объекту Ticket
     *
     * @param ticket Объект Ticket, который нужно обновить в хранилище
     * @return true в случае успешного обновления. Иначе -- false
     */
    boolean update(Ticket ticket);

    /**
     * Получить все объекты для модели Ticket для конкретного сеанса
     *
     * @param sessionId ID сеанса
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    List<Ticket> findAllBySessionId(int sessionId);

    /**
     * Удалить из хранилища объект, соответствующий передаваемому объекту Ticket
     *
     * @param ticket Объект Ticket, который необходимо удалить из хранилища
     * @return true в случае успешного удаления. Иначе -- false
     */
    boolean delete(Ticket ticket);

    /**
     * Хелпер, позволяющий определить, свободно ли конкретное место в рамках указанного сеанса
     *
     * @param sessionId Идентификатор сеанса
     * @return Бинарный предикат от номера ряда и номера места в ряду, возвращающий true,
     * если указанное место на сеансе еще не занято; иначе, предикат возвращает false
     */
    BiPredicate<Integer, Integer> placesHelper(int sessionId);
}