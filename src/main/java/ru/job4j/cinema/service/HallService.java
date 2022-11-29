package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Hall;

/**
 * Сервис, осуществляющий доступ к данным объектов модели Hall
 *
 * @see ru.job4j.cinema.model.Hall
 */
public interface HallService {

    /**
     * Получить объект-зал
     *
     * @return Объект-зал
     */
    Hall getHall();
}