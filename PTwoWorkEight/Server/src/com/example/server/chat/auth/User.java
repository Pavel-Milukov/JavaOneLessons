package com.example.server.chat.auth;

import java.util.Objects;

public class User {

    private final String login;
    private final String password;
    private final String username;

    public User (String login, String password, String username) {   // наш конструктор пользователя
        this.login = login;
        this.password = password;
        this.username = username;
    }

    public User(String login, String password) {
        this(login, password, null);
    }

    //Пользвателя нужно будет как-то сравнивать друг с другом на основании 2ух полей, сравнить пользвателя с уже существующим
    //Будем в колекции пробегать по всем пользователям которые уже есть среди авторизованных и для идентификации будет только 2 поля (логин и пароль)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
