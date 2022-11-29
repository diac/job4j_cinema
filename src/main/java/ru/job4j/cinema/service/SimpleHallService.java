package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;

import java.util.List;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Hall
 *
 * @see ru.job4j.cinema.model.Hall
 */
@Service
public class SimpleHallService implements HallService {

    /**
     * Получить объект-зал
     *
     * @return Объект-зал
     */
    public Hall getHall() {
        return new Hall(List.of(5, 10, 5, 12, 12));
    }
}