package ru.job4j.cinema.repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Session;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Session в БД
 */
@Repository
public final class JdbcSessionRepository implements SessionRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM sessions;";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM sessions WHERE id = ?;";

    private static final String ADD_QUERY = """
            INSERT INTO
                sessions(name)
            VALUES
                (?);""";

    private static final String UPDATE_QUERY = """
            UPDATE
                sessions
            SET
                name = ?
            WHERE
                id = ?;""";

    private static final String DELETE_QUERY = """
            DELETE FROM
                sessions
            WHERE
                id = ?;""";

    private static final Logger LOG = LogManager.getLogger(SessionRepository.class.getName());

    private final DataSource dataSource;

    /**
     * Конструктор для репозитория
     *
     * @param dataSource Пул подключений к БД
     */
    public JdbcSessionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Получить все записи для модели Session из БД
     *
     * @return Список сеансов. Пустой список, если ничего не найдено
     */
    @Override
    public List<Session> findAll() {
        List<Session> sessions = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    sessions.add(sessionFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return sessions;
    }

    /**
     * Получить один объект Session из БД по id
     *
     * @param id Уникальный идентификатор объекта Session
     * @return Optional для объекта Session, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Session> findById(int id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(sessionFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Добавить новую запись в БД из объекта Session
     *
     * @param session Объект Session из которого создается новая запись в БД
     * @return <span>Optional оъекта Session, соответствующего новой созданной записи в БД.
     * Optional.empty() в случае, если новую запись не удалось создать (напр., из-за нарушения
     * ссылочной целостности)</span>
     */
    @Override
    public Optional<Session> add(Session session) {
        Optional<Session> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        ADD_QUERY,
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, session.getName());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    session.setId(id.getInt(1));
                    result = Optional.of(session);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Обновить в БД запись, соответсвующую передаваемому объекту Session
     *
     * @param session Объект Session, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Session session) {
        boolean result = false;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            statement.setString(1, session.getName());
            statement.setInt(2, session.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Session
     *
     * @param session Объект Session, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Session session) {
        boolean result = false;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)
        ) {
            statement.setInt(1, session.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    private Session sessionFromResultSet(ResultSet resultSet) throws SQLException {
        return new Session(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }
}