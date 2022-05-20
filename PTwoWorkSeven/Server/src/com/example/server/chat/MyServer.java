package com.example.server.chat;

import com.example.server.chat.auth.AuthService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {                            // сам сервер будем писать в этой сущности

    private AuthService authService;                   //поле отвечающее за сервис авторизации
    private final List<ClientHandler> clients = new ArrayList<>();       // нужно хранить множество подключений,чтобы найти соединение с другим клиентом(отправить сообщение), делаем коллекцию
                                                                         // храним коллекцию обработчиков подключений <ClientHandler>

    public void start (int port) {                 //опишем некий метод котрый будет запускать, аргумент используем порт

        try (ServerSocket serverSocket = new ServerSocket(port)) {                // запускаем серверный сокет( создаем сервер ), пишем в модуле try catch с ресурсами
            System.out.println("Server has been started");                        // здесь сигнализируем, если исключения не было, что запуск сервера прошел успешно

            authService = new AuthService();                                      // запускаем сервис авторизации, инициализируем
            while (true) {             // цикл который постоянно ожидает подключение
                waitAndProcessClientConnection(serverSocket);
            }


        } catch (IOException e) {                  // ловим исключение, если не удалось подкючиться по порту
           System.err.println("Failed to bind port " + port);                     //выводим инфу об ошибке
        }
    }

    private void waitAndProcessClientConnection(ServerSocket serverSocket) throws IOException {
        // Если мы хотим, чтобы наш сервер поддерживал множество клиентов
            System.out.println("Waiting for new client connection");              // логируем для системы

            Socket clientSocket = serverSocket.accept();                          // метод по приему подключений, принимает подключение, которое пришло на сервер
            // его (IP-адрес и порт) запишется в объект типа Socket, результат этого метода кладем в переменную clientSocket
            // метод .accept() блокирует код пока не будет создано подключения ( в Java Doc есть инфа )

            System.out.println("Client has been connected");                // после того, как клиент подключился, логируем об этом

            ClientHandler clientHandler = new ClientHandler(this, clientSocket);  //создаем клиентское подключение, куда кладем экземпляр clientSocket который получили выше
                                                                                        //методом  serverSocket.accept(), передаем также через this текущий экземпляр

            clientHandler.handle();
    }

    public void broadcastMessage(String message, ClientHandler sender ) throws IOException {        //метод который будет рассылать сообщения всем клиентам

        // при отправке сообщения всем, самому себе отправялть сообщение не нужно, добавляем проверку

        for (ClientHandler client : clients) {             // берем коллекцию клиентов через for each, пробегаемся по всем
            if (client != sender)                          // проверка на то что отправитель не равен самому себе в колеекции, тогда выполняем блок кода
            client.sendMessage(message);                   // каждому элементу коллекции применяем метод отправки сообщения
        }

    }

    //при отключении клиента не убираем его из коллекции в момент отключения, поэтому делаем методы по подписке и отписке в коллекцию

    public void subscribe(ClientHandler clientHandler) {    // когда клиент подключился, в нашу коллекцию подключений добавляем сущность clientHandler
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {  // при отключени удаляем из колекции
        clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }
}
