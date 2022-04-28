package com.geekbrains.clientchat;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DataFormat;

import java.text.DateFormat;
import java.util.Date;

public class ClientController {                      // контроллер теперь как-то взаимодействует с шаблоном

    @FXML
    // это анатация добавляются к коду и наполняют его некоторым новым функционалом
    public TextField messageField;                 // поле messageField теперь есть в контроллере ( наши ID в SceneBuilder) - то что мы вводим

    @FXML
    public Button sendMessageButton;               // поле sendMessageButton теперь есть в контроллере ( наши ID в SceneBuilder)

    @FXML
    public TextArea messageTextArea;               // поле messageTextArea теперь есть в контроллере  ( наши ID в SceneBuilder) -то что написано

    @FXML
    public ListView userList;                     // список пользователей

    public void appendMessageToChat(ActionEvent actionEvent) {                  //метод отвечает за то что происходит в результате нажатия кнопки
        if (!messageField.getText().isEmpty()) {                                // если у нас поле не пустое то тогда мы добавляем текст
            messageTextArea.appendText(DateFormat.getDateTimeInstance().format(new Date())); // добавляем текущую дату
            messageTextArea.appendText(System.lineSeparator());

            // реализуется выбор пользователя которому будет отправлено сообщение
            if (!userList.getSelectionModel().isEmpty()) {                                 // если что-то выбрано (пользователь в окне)
                String sender = userList.getSelectionModel().getSelectedItem().toString();  // получаем отправителя
                messageTextArea.appendText(sender + ": ");                                         // добавляем в TextArea
            } else {
                messageTextArea.appendText("Я: ");                       // если у меня не выбран пользователь, то добавим запись
            }
            messageTextArea.appendText(messageField.getText().trim());      // там где отображаем, что написано,сюда мы добавляем с appendText то, что мы ввели в поле для ввода ( забираем из него с помощью метода getText
                                                                            // то что ввели обрезаем, trim обрезает пробелы в начале и в конце
            messageTextArea.appendText(System.lineSeparator());             //символ переноса строки во всех ОС - чтобы введенные слова в окне чата были на разных строчках
            messageTextArea.appendText(System.lineSeparator());
            requestFocus();                                                  // чтобы курсор возвращался был в поле ввода сообщения
            messageField.clear();                                           // очищаем поле для ввода после нажатия на кнопку отправить
        }
    }

    private  void requestFocus() {                                // Для фокуса ввода в строке ввода сообщения
        Platform.runLater(new Runnable() {                       //  в потоке где работает JFX Application
            @Override
            public void run() {
                messageField.requestFocus();
            }
        });
    }
}