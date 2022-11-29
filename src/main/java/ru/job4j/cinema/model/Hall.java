package ru.job4j.cinema.model;

import java.util.List;

/**
 * Модель данных кинозала
 */
public class Hall {

    /**
     * Места в зале. Список рядов, в каждом ряду доступно определенное количество мест
     */
    private List<Integer> places;

    public Hall() {
    }

    public Hall(List<Integer> places) {
        this.places = places;
    }

    public List<Integer> getPlaces() {
        return places;
    }

    public void setPlaces(List<Integer> places) {
        this.places = places;
    }
}