package ru.job4j.cinema.model;

import java.util.List;

public class Hall {

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