package com.geekbrains.clientchat.controllers;

import com.geekbrains.clientchat.ClientChat;
import com.geekbrains.clientchat.NetWork;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.function.Consumer;

//Контроллер окна авторизации
//Логика аутентификации: когда мы подключаемся к чату, сервер должен понять какой пользователь конкретно сейчас подключился
// Делаем базовую аутентификацию : показываем некое диалоговое окно, где вводим логин и пароль, сервер будет смотреть есть ли такой пользователь среди зарегистрированных, проверять логин и пароль и пускать или не пускать в чат

public class AuthController {
    private static final String AUTH_COMMAND = "/auth";
    public static final String AUTH_OK_COMMAND = "/authOk";

    @FXML
    // это анатация добавляются к коду и наполняют его некоторым новым функционалом

    public TextField loginField;      // контроллер который будет работать с шаблоном авторизации
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button authButton;

    private ClientChat clientChat;    // добавялем ссылку на ClientChat


    @FXML
    public void executeAuth() {      // нам нужно обработать тот логин и пароль который к нам пришел МЕТОД АВТОРИЗАЦИИ
        String login = loginField.getText();                         // из поля для логина забираем текст с помощью метода getText
        String password = passwordField.getText();                   // из поля для пароля забираем текст с помощью метода getText

        if (login == null || password == null || login.isBlank() || login.isBlank()) {     // проверка если ничего не введено или поле пустое считывая данные из окна

            clientChat.showErrorDialog("Логин и пароль должны быть указаны");    // в блоке вызываем ошибку
            return;                         // выходим из метода не будем ывыполнять авторизацию
        }

        // Чтобы сервер мог понять что сообщение отправлено от клиента и это сообщение связано с командой аутентификации, мы должны серверу сказать когда ты читаешь сообщение
        //и это сообщение не то, которые мы должны отправить всем пользователям, а это сообщение которое пришло от клиента с авторизацией

        String authCommandMessage = String.format("%s %s %s", AUTH_COMMAND, login, password);    // здесь формируется ссобщение которое сформировали в этом контроллере ->
                                                                     // константа AUTH_COMMAND, введеный в окне логин и пароль
        try {
            NetWork.getInstance().sendMessage(authCommandMessage);         // отправялем это сообщение серверу с командой авторизации
        } catch (IOException e) {                            // в случае ошибки даем сообщение
            clientChat.showErrorDialog("Ошибка передачи по сети");
            e.printStackTrace();
        }
    }

    public void setClientChat(ClientChat clientChat) {
        this.clientChat = clientChat;
    }

    public void initializeMessageHandler() {

        NetWork.getInstance().waitMessages(new Consumer<String>() {         //добавляем функционал
            @Override
            public void accept(String message) {         // что мы хотим сделать в том случае когда мы получаем сообщение от сервера в контр.авторизации, о том, что мы успешно авториз.
                if (message.startsWith(AUTH_OK_COMMAND)) {   // если сообщение начинается с AUTH_OK_COMMAND
                    Thread.currentThread().interrupt();      // текущее окно авторизации должны закрыть - текущий поток по показу окна авторизации

                    //Все нужно сделать гре крутиться само графическо приложение
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // задаем имя для окна
                            String[] parts = message.split(" ");
                            String userName = parts[1];
                            clientChat.getChatStage().setTitle(userName);

                            clientChat.getAuthStage().close();       // закрываем окно авторизации, закрываем окно в ом потоке где оно работает
                        }
                    });

                } else {         // если пришло сообщение не AUTH_OK_COMMAND то покажем ошибку
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            clientChat.showErrorDialog("Пользователя с таким логином и паролем не существует");
                        }
                    });
                }
            }
        });
    }
}
