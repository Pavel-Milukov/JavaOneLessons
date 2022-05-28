package com.example.server.chat;

import com.example.command.Command;
import com.example.server.chat.auth.AuthService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyServer {                            // сам сервер будем писать в этой сущности

    private AuthService authService;                   //поле отвечающее за сервис авторизации
    private final List<ClientHandler> clients = new ArrayList<>();       // нужно хранить множество подключений,чтобы найти соединение с другим клиентом(отправить сообщение), делаем коллекцию
                                                                         // храним коллекцию обработчиков подключений <ClientHandler>
    // к этой коллекции может обращаться множество потоков, для этого нужно обработать ситуацию гонки, когда один поток меняет коллекцию, а другой пытается получить состояние этой коллекции


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

    public synchronized void broadcastMessage(String message, ClientHandler sender ) throws IOException {        //метод который будет рассылать сообщения всем клиентам
        // synchronized для устранения ситуации гонки
        // при отправке сообщения всем, самому себе отправялть сообщение не нужно, добавляем проверку

        for (ClientHandler client : clients) {             // берем коллекцию клиентов через for each, пробегаемся по всем
            if (client != sender)                          // проверка на то что отправитель не равен самому себе в колеекции, тогда выполняем блок кода
            client.sendCommand(Command.clientMessageCommand(sender.getUserName(), message));                   // каждому элементу коллекции применяем метод отправки команды
        }

    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {    // метод по отправке приватного сообщения
                                                                                                                         // отправитель, получатель, приватное сообщение
       for (ClientHandler client: clients) {    // проходим по коллекции КЛИЕНТСКИХ подключений

           // клиент который итерируется не равен отправителю сообщения (самому себе не отправялем) и у клиента имя пользователя совпадает с пользователем которому мы отправялем
            if (client != sender && client.getUserName().equals(recipient)) {
                client.sendCommand(Command.clientMessageCommand(sender.getUserName(), privateMessage));
            }
       }
    }

    //метод по проверке, что текущий пользователь уже авторизован
    public synchronized boolean isUserNameBusy(String userName) {                                        // synchronized для устранения ситуации гонки
        for (ClientHandler client: clients) {    // проходим по коллекции КЛИЕНТСКИХ подключений
            if (client.getUserName().equals(userName)) {    // проверяем есть ли такой пользователь
                return true;
            }
        }
        return false;
    }

    //метод который занимается уведомлением, что список пользователей обновился (аутентификация нового пользователя или выхода из сети

    private synchronized void notifyUserListUpdated() throws IOException {
        List<String> users = new CopyOnWriteArrayList<>();       // список пользователей, котрые в данный момент авторизованы
        for (ClientHandler client : clients) {        // накапливаем список пользователей
            users.add(client.getUserName());
        }

        for (ClientHandler client : clients) { // всем подключенным пользователем скажем
            client.sendCommand(Command.updateUserListCommand(users));
        }
    }

    //при отключении клиента не убираем его из коллекции в момент отключения, поэтому делаем методы по подписке и отписке в коллекцию

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {    // когда клиент подключился, в нашу коллекцию подключений добавляем сущность clientHandler
        clients.add(clientHandler);           // synchronized для устранения ситуации гонки   здесь текущая коллекция соединений
        System.out.println("Новый пользователь подключился " + clientHandler.getUserName());
        notifyUserListUpdated();              // отправляем себе и другим что у нас появились новые пользователи
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {  // при отключени удаляем из колекции
        clients.remove(clientHandler);                                   // synchronized для устранения ситуации гонки
        notifyUserListUpdated();                                         // уведомляем, что кто-то ушел
    }

    public AuthService getAuthService() {
        return authService;
    }
}
