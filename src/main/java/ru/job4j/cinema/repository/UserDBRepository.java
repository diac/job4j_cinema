package ru.job4j.cinema.repository;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели User в БД
 */
@Repository
@ThreadSafe
public class UserDBRepository implements UserRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM users;";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?;";

    private static final String ADD_QUERY = """
            INSERT INTO
                users(username, email, phone)
            VALUES
                (?, ?, ?);""";

    private static final String UPDATE_QUERY = """
            UPDATE
                users
            SET
                username = ?,
                email = ?,
                phone = ?
            WHERE
                id = ?;""";

    private static final String DELETE_QUERY = """
            DELETE FROM
                users
            WHERE
                id = ?""";

    private final BasicDataSource pool;

    private static final Logger LOG = LogManager.getLogger(UserDBRepository.class.getName());

    /**
     * Конструктор для репозитория
     * @param pool Пул подключений к БД
     */
    public UserDBRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Получить все записи для модели User из БД
     * @return Список пользователей. Пустой список, если ничего не найдено
     */
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(userFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }

    /**
     * Получить один объект User из БД по id
     * @param id Уникальный идентификатор объекта User
     * @return Объект User, если таковой существует для переданного id. Иначе -- null
     */
    @Override
    public User findById(int id) {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return userFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Добавить новую запись в БД из объекта User
     * @param user Объект User из которого создается новая запись в БД
     * @return Объект User, соответствующий новой созданной записи в БД
     */
    @Override
    public User add(User user) {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        ADD_QUERY,
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    /**
     * Обновить в БД запсись, соответствующую передаваемому объекту User
     * @param user Объект User, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(User user) {
        boolean result = false;
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setInt(4, user.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту User
     * @param user Объект User, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(User user) {
        boolean result = false;
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)
        ) {
            statement.setInt(1, user.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    private User userFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("phone")
        );
    }
}