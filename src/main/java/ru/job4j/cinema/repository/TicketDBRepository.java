package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Ticket в БД
 */
@Repository
@ThreadSafe
public final class TicketDBRepository implements TicketRepository {

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

    private static final String FIND_ALL_BY_SESSION_ID_QUERY = """
            SELECT
                *
            FROM
                ticket
            WHERE
                session_id = ?;""";

    private static final Logger LOG = LogManager.getLogger(TicketRepository.class.getName());

    private final DataSource dataSource;

    /**
     * Конструктор для репозитория
     *
     * @param dataSource Пул подключений к БД
     */
    public TicketDBRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Получить все записи для модели Ticket из БД
     *
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(ticketFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return tickets;
    }

    /**
     * Получить один объект Ticket по id
     *
     * @param id Уникальный идентификатор объекта Ticket
     * @return Optional для объекта Ticket, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Ticket> findById(int id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(ticketFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Добавить новую запись в БД из объекта Ticket
     *
     * @param ticket Optional объекта Ticket, из которого создается новая запись в БД
     * @return <span>Optional объекта Ticket, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать (напр., из-за нарушения
     * ссылочной целостности)</span>
     */
    @Override
    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        ADD_QUERY,
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setInt(1, ticket.getSessionId());
            statement.setInt(2, ticket.getPosRow());
            statement.setInt(3, ticket.getCell());
            statement.setInt(4, ticket.getUserId());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                    result = Optional.of(ticket);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Обновить в БД запсиь, соответствующую передаваемому объекту Ticket
     *
     * @param ticket Объект Ticket, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Ticket ticket) {
        boolean result = false;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            statement.setInt(1, ticket.getSessionId());
            statement.setInt(2, ticket.getPosRow());
            statement.setInt(3, ticket.getCell());
            statement.setInt(4, ticket.getUserId());
            statement.setInt(5, ticket.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Ticket
     *
     * @param ticket Объект Ticket, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Ticket ticket) {
        boolean result = false;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)
        ) {
            statement.setInt(1, ticket.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Получить все записи для модели Ticket из БД для конкретного сеанса
     *
     * @param sessionId ID сеанса
     * @return Список билетов. Пустой список, если ничего не найдено
     */
    @Override
    public List<Ticket> findAllBySessionId(int sessionId) {
        List<Ticket> tickets = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_SESSION_ID_QUERY)
        ) {
            statement.setInt(1, sessionId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tickets.add(ticketFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return tickets;
    }

    private Ticket ticketFromResultSet(ResultSet resultSet) throws SQLException {
        return new Ticket(
                resultSet.getInt("id"),
                resultSet.getInt("session_id"),
                resultSet.getInt("pos_row"),
                resultSet.getInt("cell"),
                resultSet.getInt("user_id")
        );
    }
}