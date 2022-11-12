package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Service в репозитории
 */
@Service
@ThreadSafe
public class SessionService {

    private final SessionRepository repository;

    /**
     * Конструктор для сервиса
     *
     * @param repository Объект-репозиторий
     */
    public SessionService(SessionRepository repository) {
        this.repository = repository;
    }

    /**
     * Получить все объекты для модели Session из репозитория
     *
     * @return Список сеансов. Пустой список, если ничего не найдено
     */
    public List<Session> findAll() {
        return repository.findAll();
    }

    /**
     * Получить один объект Session из репозитория по id
     *
     * @param id Уникальный идентификатор объекта Session
     * @return Optional для объекта Session, если в репозитории существует объект для переданного id.
     * Иначе -- Optional.empty()
     */
    public Optional<Session> findById(int id) {
        return repository.findById(id);
    }

    /**
     * Добавить новый объект в репозиторий из объекта Session
     *
     * @param session Объект Session, который нужно добавить в репозиторий
     * @return Optional для объекта Session, если удалось добавить этот объект в репозиторий. Иначе -- Optional.empty()
     */
    public Optional<Session> add(Session session) {
        return repository.add(session);
    }

    /**
     * Обновить в репозитории объект, соответствующий передаваемому объекту Session
     *
     * @param session Объект Session, который нужно обновить в репозитории
     * @return true в случае успешного обновления. Иначе -- false
     */
    public boolean update(Session session) {
        return repository.update(session);
    }

    /**
     * Удалить из репозитория объект, соответствующий передаваемому объекту Session
     *
     * @param session Объект Session, который необходимо удалить из репозитория
     * @return true в случае успешного удаления. Иначе -- false
     */
    public boolean delete(Session session) {
        return repository.delete(session);
    }
}