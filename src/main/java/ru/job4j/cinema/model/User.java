package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных пользователя системы
 */
public class User {

    /**
     * Идентификатор пользователя
     */
    private int id;

    /**
     * Имя пользователя в системе
     */
    private String username;

    /**
     * Пароль пользователя в системе
     */
    private String password;

    /**
     * email пользователя
     */
    private String email;

    /**
     * Телефонный номер пользователя
     */
    private String phone;

    public User() {
    }

    public User(int id, String username, String password, String email, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}