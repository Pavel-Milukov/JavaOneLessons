package com.example.server.chat;

import com.example.command.Command;
import com.example.command.CommandType;
import com.example.command.commands.AuthCommandData;
import com.example.command.commands.PrivateMessageCommandData;
import com.example.command.commands.PublicMessageCommandData;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

    public static final String AUTH_COMMAND = "/auth";
    public static final String AUTH_OK_COMMAND = "/authOk";
    //Мы должны как-то разослать сообщения коллекции clients, поэтому из класса ClientHandler вызвать метод класса MyServer, поэтому нужно в ClientHandler иметь ссылку на MyServer
    private MyServer server;

    private final Socket clientSocket;          // подключение с клиентом идет через экземпляр Socket
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private String userName;                    // текущее имя пользователя которое обрабатывается в рамках текущего клиентского соединения

    public ClientHandler(MyServer server, Socket clientSocket) {    //приходит server в конструкторе, после этого имеет доступ к серверу
        this.server = server;                                       // когда пришел server то мы его записываем в текущее поле сервер, тот экз.класса сервер который к нам пришел
        this.clientSocket = clientSocket;
    }

    public void handle () throws IOException {                                                    // метод обработки клиентского подключения

        //Эти потоки ввода-вывода используются для передачи и приема данных.
        // разница InputStream от DataInputStream: InputStream  читает информацию побайтово методы только по чтению byte, когда мы переадем строки/числа то читать побайтово неудобно
        // поэтому взяли некий класс обертку DataInputStream который умеет на основе байтового потока принимать и читать информацию с типизацией

        // разница между DataInputStream/DataOutputStream от ObjectInputStream/ObjectOutputStream: в ObjectInputStream/ObjectOutputStream потоках можно передавать объекты,а не строки String

        inputStream = new ObjectInputStream(clientSocket.getInputStream());      //инициализируем потоки
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());   //инициализируем потоки

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
        Command command = readCommand();            // читаем команду

            // обрабатываем, что пришло в команде
        if (command == null) {
           continue;             // ждем пока пользователь отправить что-то вразумительное
        }
        if (command.getType() == CommandType.AUTH) {          // метод анализа с чего начинается команда, если авторизация
             AuthCommandData data = (AuthCommandData) command.getData();                 // из команды достаем поле data

            String login = data.getLogin();                     // получаем логин по полю объекта AuthCommandData
            String password = data.getPassword();               // получаем пароль по полю объекта AuthCommandData
            String userName = this.server.getAuthService().getUsernameByLoginAndPassword(login, password);    // получаем имя пользователя по логину и паролю

            // анализируем есть ли имя userName
            if (userName == null) {
                sendCommand(Command.errorCommand("Некорректные имя пользователя или пароль"));
            } else if (server.isUserNameBusy(userName)) {
                sendCommand(Command.errorCommand("Такой пользователь уже существует"));              // отправялем команду с типом ошибка
            }else  {                             // если имя не null мы успешно авторизовались
                this.userName = userName;        //поля этого класса равно результату метода по получению логина и пароля
                sendCommand(Command.authOkCommand(userName));   // отправялем команду об успешной авторизации,вызываем статический метод authOkCommand
                server.subscribe(this);                                   // подписываемся на себя же
                return;                     // выходим из этого метода
            }
        }

        }
    }

    //метод по чтению команд
    private Command readCommand() throws IOException {
        Command command = null;

        try {
            command = (Command) inputStream.readObject();     // из потока можем прочитать уже объект с помощью readObject() и его кастим к нужному типу Command
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to read Command class");  // если каст не удался
            e.printStackTrace();
        }
        return command;
    }

    //метод по чтению сообщений от клиента
    private void readMessage() throws IOException {         //метод по чтению сообщений
        while (true) {                                      // читаем в бесконечном цикле
            Command command = readCommand();                // получаем команду
            if (command == null) {                          // провекра если null то идем выполнять следующую итерацию цикла
                continue;
            }
            switch (command.getType()) {

                // когда от клиента получаем приватное сообщение
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();    // получили переменную с данными
                    String receiver = data.getReceiver();                                              // извелкаем получателя
                    String privateMessage = data.getMessage();                                         // извлекаем так скажем личное сообщение
                    server.sendPrivateMessage(this, receiver, privateMessage);    // с помощью метода отправки приватного сообщения отправляем это сообщение, this отправитель это мы
                    break;
                }
                    // когда от клиента получаем публичное сообщение
                case PUBLIC_MESSAGE:
                    PublicMessageCommandData data = (PublicMessageCommandData) command.getData();        // получили переменную с данными
                    processMessage(data.getMessage());                               // с помощью этого метода рассылаем всем подключенным участникам сообщение, извлекаем сообщение
                    break;
            }
        }
    }

    private void processMessage(String message) throws IOException {           // метод по рассылке всем сообщения
        this.server.broadcastMessage(message, this);                     //разошлем сообщение всему чату с помощью метода broadcastMessage, передаем сообщение и текущий экземпляр
    }

    //метод отправялет команду
    public void sendCommand (Command command) throws IOException {
        outputStream.writeObject(command);                                // в потока можем записать уже объект с помощью writeObject
    }

    private void closeConnection() throws IOException {               //метод по отключению от сервера
        outputStream.close();
        inputStream.close();
        server.unsubscribe(this);                          // отписываемся из коллекции
        clientSocket.close();
    }

    public String getUserName() {
        return userName;
    }
}
