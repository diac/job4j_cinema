package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.cinema.model.Session;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий, отвечающий за сериализацию/десериализацию объектов модели Session в БД
 */
public class SessionDBRepository implements SessionRepository {

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

    private final BasicDataSource pool;

    /**
     * Конструктор для репозитория
     * @param pool Пул подключений к БД
     */
    public SessionDBRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Получить все записи для модели Session из БД
     * @return Список сеансов. Пустой список, если ничего не найдено
     */
    @Override
    public List<Session> findAll() {
        return null;
    }

    /**
     * Получить один объект Session из БД по id
     * @param id Уникальный идентификатор объекта Session
     * @return Optional для объекта Session, если в БД существует запись для переданного id. Иначе -- Optional.empty()
     */
    @Override
    public Optional<Session> findById(int id) {
        return Optional.empty();
    }

    /**
     * Добавить новую запись в БД из объекта Session
     * @param session Объект Session из которого создается новая запись в БД
     * @return Объект Session, соответствующий новой созданной записи в БД
     */
    @Override
    public Session add(Session session) {
        return null;
    }

    /**
     * Обновить в БД запись, соотвутетсвующую передаваемому объекту Session
     * @param session Объект Session, для которого необходимо обновить запись в БД
     * @return true в случае успешного обновления. Иначе -- false
     */
    @Override
    public boolean update(Session session) {
        return false;
    }

    /**
     * Удалить из БД запись, соответствующую передаваемому объекту Session
     * @param session Объект Session, для которого необходимо удалить запись из БД
     * @return true в случае успешного удаления. Иначе -- false
     */
    @Override
    public boolean delete(Session session) {
        return false;
    }
}