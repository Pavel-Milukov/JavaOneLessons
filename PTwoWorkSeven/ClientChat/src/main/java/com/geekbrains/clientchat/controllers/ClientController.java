package com.geekbrains.clientchat.controllers;

import com.geekbrains.clientchat.ClientChat;
import com.geekbrains.clientchat.NetWork;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class ClientController {                      // контроллер теперь как-то взаимодействует с шаблоном

    @FXML
    // это анатация добавляются к коду и наполняют его некоторым новым функционалом
    public TextField messageTextArea;                 // поле messageField теперь есть в контроллере ( наши ID в SceneBuilder) - то что мы вводим

    @FXML
    public Button sendMessageButton;               // поле sendMessageButton теперь есть в контроллере ( наши ID в SceneBuilder)

    @FXML
    public TextArea chatTextArea;               // поле messageTextArea теперь есть в контроллере  ( наши ID в SceneBuilder) -то что написано

    @FXML
    public ListView userList;                     // список пользователей

    private ClientChat application;

    public void sendMessage() {                   // отображения сообщений
        String message = messageTextArea.getText();

        if(message.isEmpty()) {                    // если сообщение пустое, выходим из метода
            messageTextArea.clear();
            return;
        }
            // анализируем отправителя сообщения
        String sender = null;
        if (!userList.getSelectionModel().isEmpty()) {
            sender = userList.getSelectionModel().getSelectedItem().toString();
        }
        try {
            message = sender != null ? String.format(": ", sender, message) : message; // если sender не null то сообщение будет выглядить так Server: message
            NetWork.getInstance().sendMessage(message);
        } catch (IOException e) {
            application.showErrorDialog("Ошибка передачи данных по сети");
        }
        appendMessageToChat("Я", message);
    }


    public void initializeMessageHandler() {
        NetWork.getInstance().waitMessages(new Consumer<String>() {         //добавляем функционал
            @Override
            public void accept(String message) {
                appendMessageToChat("Server", message);
            }
        });
    }

    public ClientChat getApplication() {
        return application;
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }

    public void appendMessageToChat(String sender, String message) {                  //метод отвечает за то что происходит в результате нажатия кнопки

        chatTextArea.appendText(DateFormat.getTimeInstance().format(new Date()));
        chatTextArea.appendText(System.lineSeparator());

        if (sender != null) {
            chatTextArea.appendText(sender + ":");
            chatTextArea.appendText(System.lineSeparator());      // добавление переноса в окне чата
        }

        chatTextArea.appendText(message);
        chatTextArea.appendText(System.lineSeparator());          // добавление переноса в окне чата
        chatTextArea.appendText(System.lineSeparator());
        messageTextArea.requestFocus();
        messageTextArea.clear();

        // ЗАКОМЕНЧЕНО ДЛЯ СЕБЯ!!!
//        if (!messageTextArea.getText().isEmpty()) {                                // если у нас поле не пустое то тогда мы добавляем текст
//            chatTextArea.appendText(DateFormat.getDateTimeInstance().format(new Date())); // добавляем текущую дату
//            chatTextArea.appendText(System.lineSeparator());
//
//            // реализуется выбор пользователя которому будет отправлено сообщение
//            if (!userList.getSelectionModel().isEmpty()) {                                 // если что-то выбрано (пользователь в окне)
//                String sender = userList.getSelectionModel().getSelectedItem().toString();  // получаем отправителя
//                chatTextArea.appendText(sender + ": ");                                         // добавляем в TextArea
//            } else {
//                chatTextArea.appendText("Я: ");                       // если у меня не выбран пользователь, то добавим запись
//            }
//            chatTextArea.appendText(messageTextArea.getText().trim());      // там где отображаем, что написано,сюда мы добавляем с appendText то, что мы ввели в поле для ввода ( забираем из него с помощью метода getText
//                                                                            // то что ввели обрезаем, trim обрезает пробелы в начале и в конце
//            chatTextArea.appendText(System.lineSeparator());             //символ переноса строки во всех ОС - чтобы введенные слова в окне чата были на разных строчках
//            chatTextArea.appendText(System.lineSeparator());
//            requestFocus();                                                  // чтобы курсор возвращался был в поле ввода сообщения
//            messageTextArea.clear();                                           // очищаем поле для ввода после нажатия на кнопку отправить
//        }


    }
}