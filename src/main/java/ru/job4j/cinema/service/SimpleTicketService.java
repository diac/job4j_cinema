package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Ticket в репозитории.<br>
 * Данный класс является потокобезопасным. Потенциальная проблема с многопоточностью в методе add(),
 * т.е. проблема одновременной покупки разных билетов на одно и то же место, решается на уровне БД
 * путем использования ограничения уникальности.
 *
 * @see ru.job4j.cinema.model.Ticket
 * @see ru.job4j.cinema.repository.TicketRepository
 */
@Service
@ThreadSafe
public class SimpleTicketService implements TicketService {

    private final TicketRepository repository;

    /**
     * Конструктор для сервиса
     *
     * @param repository Объект-репозиторий
     */
    public SimpleTicketService(TicketRepository repository) {
        this.repository = repository;
    }

    /**
     * Получить все объекты для модели Ticket из репозитория
     *
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    public List<Ticket> findAll() {
        return repository.findAll();
    }

    /**
     * Получить один объект Ticket из репозитория по id
     *
     * @param id Уникальный идентификатор объекта Ticket
     * @return Optional для объекта Ticket, если в репозитории существует объект для переданного id.
     * Иначе -- Optional.empty()
     */
    public Optional<Ticket> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Добавить новый объект в репозиторий из объекта Ticket
     *
     * @param ticket Объект Ticket, который нужно добавить в репозиторий
     * @return Optional для объекта Ticket, если удалось добавить этот объект в репозиторий. Иначе -- Optional.empty()
     */
    public Optional<Ticket> add(Ticket ticket) {
        return repository.add(ticket);
    }

    /**
     * Обновить в репозитории объект, соответствующий передаваемому объекту Ticket
     *
     * @param ticket Объект Ticket, который нужно обновить в репозитории
     * @return true в случае успешного обновления. Иначе -- false
     */
    public boolean update(Ticket ticket) {
        return repository.update(ticket);
    }

    /**
     * Получить все объекты для модели Ticket из репозитория для конкретного сеанса
     *
     * @param sessionId ID сеанса
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    public List<Ticket> findAllBySessionId(int sessionId) {
        return repository.findAllBySessionId(sessionId);
    }

    /**
     * Удалить из репозитория объект, соответствующий передаваемому объекту Ticket
     *
     * @param ticket Объект Ticket, который необходимо удалить из репозитория
     * @return true в случае успешного удаления. Иначе -- false
     */
    public boolean delete(Ticket ticket) {
        return repository.delete(ticket);
    }

    /**
     * Хелпер, позволяющий определить, свободно ли конкретное место в рамках указанного сеанса
     *
     * @param sessionId Идентификатор сеанса
     * @return Бинарный предикат от номера ряда и номера места в ряду, возвращающий true,
     * если указанное место на сеансе еще не занято; иначе, предикат возвращает false
     */
    public BiPredicate<Integer, Integer> placesHelper(int sessionId) {
        record Place(int row, int place) {
        }
        Set<Place> occupiedPlaces = new HashSet<>();
        List<Ticket> tickets = findAllBySessionId(sessionId);
        tickets.forEach(
                ticket -> occupiedPlaces.add(new Place(ticket.getPosRow(), ticket.getCell()))
        );
        return (posRow, cell) -> !occupiedPlaces.contains(new Place(posRow, cell));
    }
}