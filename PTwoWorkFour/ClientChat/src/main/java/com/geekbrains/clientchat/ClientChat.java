package com.geekbrains.clientchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientChat extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientChat.class.getResource("chat-template.fxml")); // шаблон отвечающий за расстановку компонентов переменная хранит ссылку на шаблон
        Scene scene = new Scene(fxmlLoader.load()); // один из видов конструктора который принимает файл с шваблоном
        stage.setTitle("Java FX Application");
        stage.setScene(scene);

        ClientController controller = fxmlLoader.getController();              // получаем контроллер
        controller.userList.getItems().addAll("user1", "user2");          // у контроллера получаем userList

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}