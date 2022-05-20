package com.example.server.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    public static final String AUTH_COMMAND = "/auth";
    public static final String AUTH_OK_COMMAND = "/authOk";
    //Мы должны как-то разослать сообщения коллекции clients, поэтому из класса ClientHandler вызвать метод класса MyServer, поэтому нужно в ClientHandler иметь ссылку на MyServer
    private MyServer server;

    private final Socket clientSocket;          // подключение с клиентом идет через экземпляр Socket
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public ClientHandler(MyServer server, Socket clientSocket) {    //приходит server в конструкторе, после этого имеет доступ к серверу
        this.server = server;                                       // когда пришел server то мы его записываем в текущее поле сервер, тот экз.класса сервер который к нам пришел
        this.clientSocket = clientSocket;
    }

    public void handle () throws IOException {                                                    // метод обработки клиентского подключения

        //Эти потоки ввода-вывода используются для передачи и приема данных.
        // разница InputStream от DataInputStream: InputStream  читает информацию побайтово методы только по чтению byte, когда мы переадем строки/числа то читать побайтово неудобно
        // поэтому взяли некий класс обертку DataInputStream который умеет на основе байтового потока принимать и читать информацию с типизацией

        inputStream = new DataInputStream(clientSocket.getInputStream());      //инициализируем потоки
        outputStream = new DataOutputStream(clientSocket.getOutputStream());   //инициализируем потоки

        //В один и тот же момент у нас клиент может отправить сообщение и мы должны обрабатывать каждого клиента, так как метод запускается в потоке маин то текущий поток будет всегда
        // блокироваться и вся работа будет в ожидании нового подключения и если мы хотим одновременно и ожидать и обрабатывать новое подключение то нам нужно все делать в отдельном потоке

        //Создаем поток где мы обрабатываем подключение клиента (ожидание сообщение клиента)   -> ProcessClientConnection так скажем вторая логическая часть нашего метода
        new Thread(() -> {
                try {
                    authenticate();                       // выполняем авторизацию
                    readMessage();                       // читаем сообщение
                } catch (IOException e) {                // обрабатываем ситуацию когда произошла ошибка при чтении сообщения
                    System.err.println("Failed to process message from client");
                    e.printStackTrace();
                } finally {                                // блок исполняется всегда не забываю
                    try {
                        closeConnection();                 // после того как закончили читать сообщение закрыть соединение
                    } catch (IOException e) {
                        System.err.println("Failed to close connection");
                    }
                }
        }).start();                       // запускаем поток
    }

    private void authenticate() throws IOException {        // метод авторизации
        while (true) {                              // в бесконечном цикле анализируем сообщения
        String message = inputStream.readUTF();     // получаем сообщение
        if (message.startsWith(AUTH_COMMAND)) {          // метод анализа с чего начинается строка
                                                         // будем заниматься авторизацией -> анализированием сообщения
            String[] parts = message.split(" ");   // метод разбиения строки на слова с некоторым делителем, split разобъет строку и вернет массив строк  с помощью разделителя " "
            String login = parts[1];                     // логин на первом месте
            String password = parts[2];                  // пароль на втором
            String userName = this.server.getAuthService().getUsernameByLoginAndPassword(login, password);    // получаем имя пользователя по логину и паролю

            // анализируем есть ли имя userName
            if (userName == null) {
                sendMessage("Некорректные логин и пароль");
            } else {                             // если имя не null мы успешно авторизовались
                sendMessage(String.format("%s %s", AUTH_OK_COMMAND, userName));      // отправялем сообщение об успешной аворизации
                server.subscribe(this);                                   // подписываемся на себя же
                return;                     // выходим из этого метода
            }
        }

        }
    }

    private void readMessage() throws IOException {         //метод по чтению сообщений
        while (true) {                                      // читаем в бесконечном цикле
            String message = inputStream.readUTF().trim();           // будем получать сообщение, обрезаем от пробелов с помощью .trim()
            System.out.println("message = " + message);              // логируем
            if (message.startsWith("/end")) {               // ситуация когда пользователь хочет закрыть чат
                return;                                     // выходим из бесконечного цикла
            } else {                                        // иначе обрабатываем сообщение
                processMessage(message);
            }
        }
    }

    private void processMessage(String message) throws IOException {           // обработка самого сообщения, анализируем
        this.server.broadcastMessage(message, this);                     //разошлем сообщение всему чату, передаем сообщение и текущий экземпляр
    }

    public void sendMessage (String message) throws IOException {     // метод отправки сообщения
        this.outputStream.writeUTF(message);                          // отправялем сообщение в исходящий поток
    }

    private void closeConnection() throws IOException {               //метод по отключению от сервера
        outputStream.close();
        inputStream.close();
        server.unsubscribe(this);                          // отписываемся из коллекции
        clientSocket.close();
    }
}
