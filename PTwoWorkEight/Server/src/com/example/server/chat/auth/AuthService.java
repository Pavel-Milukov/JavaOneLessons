package com.example.server.chat.auth;

// Когда пришло сообщение на авторизацию ( AUTH_COMMAND, логин и пароль ) нужно эти данные обрабатывать

import java.util.Set;

public class AuthService {     // сервис авторизации  -> итог здесь должны вернуть имя клиента по логину и паролю

    private static Set<User> USERS = Set.of(                                         // коллекция пользователей
            new User("login1", "pass1", "username1"),
            new User("login2", "pass2", "username2"),
            new User("login3", "pass3", "username3")
    );

    public String getUsernameByLoginAndPassword(String login, String password) {    // получаем имя пользователя по паролю и логину
        User requiredUser = new User(login, password);  // создали абстрактного пользователя используемого для проверки
        for (User user : USERS) {                       // пробегаемся по коллекции пользователей
            if (requiredUser.equals(user)) {                // пользователь который присутсвует равен пользователю
                return user.getUsername();              // получили имя пользователя
            }
        }
        return null;                // если ничего не нашли возввращаем null
    }
}
