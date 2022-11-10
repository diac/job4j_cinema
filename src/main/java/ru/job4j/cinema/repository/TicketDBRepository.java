package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Ticket в БД
 */
@Repository
@ThreadSafe
public class TicketDBRepository implements TicketRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM ticket;";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM ticket WHERE id = ?;";

    private static final String ADD_QUERY = """
            INSERT INTO
                ticket(session_id, pos_row, cell, user_id)
            VALUES
                (?, ?, ?, ?);""";

    private static final String UPDATE_QUERY = """
            UPDATE
                ticket
            SET
                session_id = ?,
                pos_row = ?,
                cell = ?,
                user_id = ?
            WHERE
                id = ?;""";

    private static final String DELETE_QUERY = """
            DELETE FROM
                ticket
            WHERE
                id = ?;""";

    private static final Logger LOG = LogManager.getLogger(TicketRepository.class.getName());

    private final BasicDataSource pool;

    /**
     * Конструктор для репозитория
     *
     * @param pool Пул подключений к БД
     */
    public TicketDBRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Получить все записи для модели Ticket из БД
     *
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    @Override
    public List<Ticket> findAll() {
        return null;
    }

    /**
     * Получить один объект Ticket по id
     *
     * @param id Уникальный идентификатор объекта Ticket
     * @return Optional для объекта Ticket, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Ticket> findById(int id) {
        return Optional.empty();
    }

    /**
     * Добавить новую запись в БД из объекта Ticket
     *
     * @param ticket Объект Ticket, из которого создается новая запись в БД
     * @return Объект Ticket, соответствующий новой созданной записи в БД
     */
    @Override
    public Ticket add(Ticket ticket) {
        return null;
    }

    /**
     * Обновить в БД запсиь, соответствующую передаваемому объекту Ticket
     *
     * @param ticket Объект Ticket, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Ticket ticket) {
        return false;
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Ticket
     * @param ticket Объект Ticket, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Ticket ticket) {
        return false;
    }
}