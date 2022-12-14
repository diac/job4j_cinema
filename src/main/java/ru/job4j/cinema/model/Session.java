package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных сеансов
 */
public class Session {

    /**
     * Идентификатор сеанса
     */
    private int id;

    /**
     * Имя сеанса
     */
    private String name;

    public Session() {
    }

    public Session(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}