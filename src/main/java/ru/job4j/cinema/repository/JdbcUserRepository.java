package ru.job4j.cinema.repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели User в БД
 */
@Repository
public final class JdbcUserRepository implements UserRepository {

    private static final String FIND_ALL_QUERY = "SELECT * FROM users;";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?;";

    private static final String ADD_QUERY = """
            INSERT INTO
                users(username, password, email, phone)
            VALUES
                (?, ?, ?, ?);""";

    private static final String UPDATE_QUERY = """
            UPDATE
                users
            SET
                username = ?,
                password = ?,
                email = ?,
                phone = ?
            WHERE
                id = ?;""";

    private static final String DELETE_QUERY = """
            DELETE FROM
                users
            WHERE
                id = ?""";

    private static final String FIND_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?;";

    private static final String FIND_BY_USERNAME_AND_PASSWORD_QUERY =
            "SELECT * FROM users WHERE username = ? AND password = ?;";

    private static final Logger LOG = LogManager.getLogger(JdbcUserRepository.class.getName());

    private final DataSource dataSource;

    /**
     * Конструктор для репозитория
     *
     * @param dataSource Пул подключений к БД
     */
    public JdbcUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Получить все записи для модели User из БД
     *
     * @return Список пользователей. Пустой список, если ничего не найдено
     */
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
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
     *
     * @param id Уникальный идентификатор объекта User
     * @return Optional для объекта User, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<User> findById(int id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Добавить новую запись в БД из объекта User
     *
     * @param user Объект User, из которого создается новая запись в БД
     * @return Optional для объекта User, если удалось создать соответствующую запись в БД. Иначе -- Optional.empty()
     */
    @Override
    public Optional<User> add(User user) {
        Optional<User> result = Optional.empty();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        ADD_QUERY,
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhone());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    result = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Обновить в БД запись, соответствующую передаваемому объекту User
     *
     * @param user Объект User, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(User user) {
        boolean result = false;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhone());
            statement.setInt(5, user.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту User
     *
     * @param user Объект User, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(User user) {
        boolean result = false;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)
        ) {
            statement.setInt(1, user.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Найти пользователя по имени пользователя
     *
     * @param username Имя пользователя
     * @return Optional объекта User. Optional.empty(), если пользователь не найден
     */
    @Override
    public Optional<User> findByUsername(String username) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_QUERY)
        ) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    /**
     * Найти пользователя по имени пользователя и паролю
     *
     * @param username Имя пользователя
     * @param password Пароль пользователя
     * @return Optional объекта User. Optional.empty(), если пользователь не найден
     */
    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_AND_PASSWORD_QUERY)
        ) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(userFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private User userFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("phone")
        );
    }
}