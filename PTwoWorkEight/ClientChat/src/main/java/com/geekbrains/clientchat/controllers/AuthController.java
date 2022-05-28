package com.geekbrains.clientchat.controllers;

import com.example.command.Command;
import com.example.command.CommandType;
import com.example.command.commands.AuthOkCommandData;
import com.geekbrains.clientchat.ClientChat;
import com.geekbrains.clientchat.dialogs.Dialogs;
import com.geekbrains.clientchat.model.NetWork;
import com.geekbrains.clientchat.model.ReadMessageListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

//Контроллер окна авторизации
//Логика аутентификации: когда мы подключаемся к чату, сервер должен понять какой пользователь конкретно сейчас подключился
// Делаем базовую аутентификацию : показываем некое диалоговое окно, где вводим логин и пароль, сервер будет смотреть есть ли такой пользователь среди зарегистрированных, проверять логин и пароль и пускать или не пускать в чат

public class AuthController {


    @FXML
    // это анатация добавляются к коду и наполняют его некоторым новым функционалом

    public TextField loginField;      // контроллер который будет работать с шаблоном авторизации
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button authButton;

    public ReadMessageListener readMessageListener;

    @FXML
    public void executeAuth() {      // нам нужно обработать тот логин и пароль который к нам пришел МЕТОД АВТОРИЗАЦИИ
        String login = loginField.getText();                         // из поля для логина забираем текст с помощью метода getText
        String password = passwordField.getText();                   // из поля для пароля забираем текст с помощью метода getText

        if (login == null || password == null || login.isBlank() || login.isBlank()) {     // проверка если ничего не введено или поле пустое считывая данные из окна

            Dialogs.AuthError.EMPTY_CREDENTIAL.show();              // логика показа ошибки
            return;                         // выходим из метода не будем ывыполнять авторизацию
        }

        if (!isConnectedToServer()) {        // проверка подключены ли мы к серверу
            Dialogs.NetWorkError.SERVER_CONNECT.show();
        }

        // Чтобы сервер мог понять что сообщение отправлено от клиента и это сообщение связано с командой аутентификации, мы должны серверу сказать когда ты читаешь сообщение
        //и это сообщение не то, которые мы должны отправить всем пользователям, а это сообщение которое пришло от клиента с авторизацией

            // здесь формируется ссобщение которое сформировали в этом контроллере ->
                                                                     // константа AUTH_COMMAND, введеный в окне логин и пароль
        try {
            NetWork.getInstance().sendAuthMessage(login, password);         // отправялем это сообщение серверу с командой авторизации
        } catch (IOException e) {                            // в случае ошибки даем сообщение
            Dialogs.NetWorkError.SEND_MESSAGE.show();
            e.printStackTrace();
        }
    }

    public void initializeMessageHandler() {

       readMessageListener = getNetWork().addReadMessageListener(new ReadMessageListener() {         //навешиваем нового обработчика когда приходит сообщение от сервера
            @Override
            public void processReceivedCommand(Command command) {         // что мы хотим сделать в том случае когда мы получаем команду от сервера в контр.авторизации, о том, что мы успешно авториз.
                if (command.getType() == CommandType.AUTH_OK) {   // если у команды gettype = AUTH_OK
                    // задаем имя для окна
                    AuthOkCommandData data = (AuthOkCommandData) command.getData();   // извлекаем данные
                    String userName = data.getUserName();

                    //Все нужно сделать гре крутиться само графическо приложение
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ClientChat.getInstance().switchToMainChatWindows(userName);
                        }
                    });

                } else {         // если пришло команда не AUTH_OK то покажем ошибку
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Dialogs.AuthError.INVALID_CREDENTIALS.show();    //передаем нашу ошибку
                        }
                    });
                }
            }
        });
    }

    //метод который отвечает за проверку состояние подключения к серверу
    public boolean isConnectedToServer() {
        NetWork netWork = getNetWork();     // получаем экземляр класса NetWork
        return netWork.isConnected() || netWork.connect();
    }

    //метод возвращает экземпляр network
    private NetWork getNetWork() {
      return NetWork.getInstance();
    }

    public void close() {
        getNetWork().removeReadMessageListener(readMessageListener); //удалим текущий MessageListener
    }
}
