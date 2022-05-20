package com.geekbrains.clientchat;

import com.geekbrains.clientchat.controllers.AuthController;
import com.geekbrains.clientchat.controllers.ClientController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class ClientChat extends Application {

    private Stage chatStage;
    private Stage authStage;

    @Override
    public void start(Stage primaryStage) throws IOException {         // ClientController создается в этом методе
        this.chatStage = primaryStage;

        // Раделяем логику по инициализации окна для чата и для окна авторизации c помощью двух созданных методов: createChatDialog и createAuthDialog

        ClientController controller = createChatDialog(primaryStage);
        connectToServer(controller);             // вызываем метод connectToServer куда передаем текущий контроллер
        createAuthDialog(primaryStage);

        controller.initializeMessageHandler();
    }

    private void createAuthDialog(Stage primaryStage) throws IOException {
        // описание создания окна авторизации
        FXMLLoader authLoader = new FXMLLoader();     // получаем лоудер  шаблона
        authLoader.setLocation(ClientChat.class.getResource("authDialog.fxml"));  // читаем шаблон который мы приложили ( шаблон окна авторизации )
        AnchorPane authDialogPanel = authLoader.load();

        // Содаем отдельное окно /Stage которое будем отображать окно авторизации
        authStage = new Stage();
        authStage.initOwner(primaryStage);    //окно будет дочерним от основного
        authStage.initModality(Modality.WINDOW_MODAL);     // окно верхнего уровня, пока окно авторизации не закроем, мы не сможем переместиться на родительское окно

        authStage.setScene(new Scene(authDialogPanel));    // создаем новую сцену

        AuthController authController = authLoader.getController();   // получаем контроллер авторизации
        authController.setClientChat(this);
        authController.initializeMessageHandler();

        authStage.showAndWait();
    }

    private ClientController createChatDialog(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();   // получаем лоудер данного шаблона
        fxmlLoader.setLocation(ClientChat.class.getResource("chat-template.fxml")); // читаем шаблон отвечающий за расстановку компонентов переменная хранит ссылку на шаблон

        Parent root = fxmlLoader.load();        // загружаем его

        Scene scene = new Scene(root); // один из видов конструктора который принимает файл с шваблоном
        this.chatStage.setTitle("Java FX Application");
        this.chatStage.setScene(scene);

        ClientController controller = fxmlLoader.getController();              // получаем контроллер
        controller.userList.getItems().addAll("user1", "user2");          // у контроллера получаем userList

        primaryStage.show();     // показываем окно
        return controller;
    }

    private void connectToServer(ClientController clientController) {          // метод по подключение к серверу, здесь будем передавать сообщения в контроллер ( он отображает тот текст,
                                                                               // который мы ввели в окно чата

        boolean resultConnectedToServer = NetWork.getInstance().connect();     // результат подключения к серверу
            if (!resultConnectedToServer) {                                    // если метод выше вернул false, то выводим сообщение
                String errorMessage = "Невозможно установить сетевое соединение";
                System.err.println(errorMessage);
                showErrorDialog(errorMessage);
            }


            clientController.setApplication(this);

            chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {           // по закрытию окна нужно закрыть сетевое соединение
                @Override
                public void handle(WindowEvent windowEvent) {
                    NetWork.getInstance().close();
                }
            });
    }

    public void showErrorDialog (String message) {             // метод котоое создает  модальное окно где будут отображаться ошибки, он будет принимать некое сообщение
        Alert alert = new Alert(Alert.AlertType.ERROR);         // используем класс Alert который будет всплывающее окно показывать
        alert.setTitle("Ошибка");                               //окну можем показать  заголовок что произошла ошибка
        alert.setContentText(message);                          //текст будем передавать в параметрах метода и этот текст положим в объект alert в поле setContentText
        alert.showAndWait();                                    // жди пока будет закрыто данное окно
    }

    public static void main(String[] args) {
        launch();
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public Stage getChatStage() {
        return chatStage;
    }
}