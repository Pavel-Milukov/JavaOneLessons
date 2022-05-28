package com.geekbrains.clientchat.dialogs;

import com.geekbrains.clientchat.ClientChat;
import javafx.scene.control.Alert;

public class Dialogs {                  //класс который будет управлять сообщениями для пользователя

    public enum AuthError {             // перечисление о ошибке авторизации
        EMPTY_CREDENTIAL("Логин и пароль должны быть указаны"),
        INVALID_CREDENTIALS("Логин и пароль заданы некорректно");


        private  static final String TITLE = "Ошибка аутентификации";   // статическое поле, заголовок для окна котрое будем показывать
        private  static final String TYPE = TITLE;

        private final String message;  //его будем отправлять

        AuthError(String message) {
            this.message = message;
        }

        public void show() {                                          //будет из enum запускать показ этого окна
            showDialog(Alert.AlertType.ERROR, TITLE, TITLE, message); // передаем сообщение об ошибке, заголовок, и поле message для объекта enum
        }
    }

    public enum NetWorkError {                                              // перечисление об ошибках соединения
        SEND_MESSAGE("Не удалось отправить сообщение!"),
        SERVER_CONNECT("Не удалось установить соединение с сервером!");


        private static final String TITLE = "Сетевая ошибка";
        private static final String TYPE = "Ошибка передачи данных по сети";
        private final String message;

        NetWorkError(String message) {
            this.message = message;
        }

        public void show() {                                          //будет из enum запускать показ этого диалогового окна
            showDialog(Alert.AlertType.ERROR, TITLE, TYPE, message); // передаем сообщение об ошибке, заголовок, и поле message для объекта enum
        }
    }

    private static void showDialog(Alert.AlertType dialogType, String title, String type, String message) {     // параметры диалогового окна, формируется модальное окно
        Alert alert = new Alert(dialogType);                    // используем класс Alert который будет всплывающее окно показывать
        alert.initOwner(ClientChat.getInstance().getChatStage()); // основное окно котрое мы показываем будет привязано к родительскому окну
        alert.setTitle(title);                                   //окну можем показать  заголовок что произошла ошибка
        alert.setHeaderText(type);
        alert.setContentText(message);                          //текст будем передавать в параметрах метода и этот текст положим в объект alert в поле setContentText
        alert.showAndWait();                                    // жди пока будет закрыто данное окно
    }



}
