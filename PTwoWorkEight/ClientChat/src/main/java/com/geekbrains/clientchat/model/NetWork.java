package com.geekbrains.clientchat.model;


import com.example.command.Command;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetWork {  // умеет подключаться к серверу,здесь будем описывать подключение к серверу, логику по отправке данных на сервер и логику по чтению данных из сервера, будет
                        // использоваться с классом ClientChat работа с сетевыми соединениями

    private List<ReadMessageListener> listeners = new CopyOnWriteArrayList<>();       //коллекция обработчиков сообщений, делаем потокобезопасный лист

    private static NetWork INSTANCE;    // релизуем сущность NetWork чтобы во всех упоминаиях о ней присутствовал один и тот же экземпляр данной сущности, чтобы не создали несколько классво подключений

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 12_001;


    private int port;
    private String host;

    private Socket socket;
    private ObjectInputStream inputStream;                   // потоки запоминаются в поле класса
    private ObjectOutputStream outputStream;                 // потоки запоминаются в поле класса
    private Thread readMessageProcess;                     // поле отвечающее за текущий поток обработки сообщения
    private boolean connected;                              // флаг подключены мы к серверу или нет

    private NetWork (String host, int port) {               // создаем параметризированный конструктор
        this.host=host;
        this.port=port;
    }

    private NetWork() {                                     // конструктор с данными по умолчанию
       this(SERVER_HOST, SERVER_PORT);
    }

    public static NetWork getInstance() {                  // с любого места сможем вызывать метод который будет возвращать текущий экземпляр согласно патерному сингл тону
        if (INSTANCE == null) {                            // если поле null первый вызов, то мы инициализиурем эту сущность
            INSTANCE = new NetWork();
        }

        return INSTANCE; // возвращаем значение текущего поля
    }

    public boolean connect () {                        //метод который говорит удалось ли подключится на сервер или нет, здесь происходит подключение к серверу
        try {
            socket = new Socket(host, port);      // создаем клиентский сокет,указываем адрес и порт нашего сервера, инициализируем подключение к серверу

            // Создаем потоки на ввод и на вывод ВАЖНО! объявление данных потоков на сервере и клиенте должно быть input на дной стороне, а output на другой для работы соединения
            outputStream = new ObjectOutputStream(socket.getOutputStream());                //запись, инициализируем потоки
            inputStream = new ObjectInputStream(socket.getInputStream());                   //чтение, инициализируем потоки
            readMessageProcess = startReadMessageProcess();
            // метод который будет создавать отдельный поток
            connected = true;                               // когда успешно подключились ставим флаг в true
            return true;                                    // если удалось подключиться, то возвращаем истина
        } catch (IOException e) {
            e.printStackTrace();
            return false;                                    //если не удалось подключиться то возвращаем ложь
        }
    }

    //метод по отправке приватного сообщения
    public void sendPrivateMessage(String receiver, String message) throws IOException {
        sendCommand(Command.privateMessageCommand(receiver, message)); //метод по отправке приватного сообщения на сервер
    }


    public void sendCommand(Command command) throws IOException {    //метод по отправке команды
        try {
            outputStream.writeObject(command);                     // отправим команду по сети
        } catch (IOException e) {
            System.err.println("Не удалось отправить сообщение на сервер");
            e.printStackTrace();
            throw e;
        }
    }

    //метод по чтению команды
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


    // создаем функционал по отправке сообщений на сервер по сети

    public void sendMessage(String message) throws IOException {    // отправялется публичное сообщение
        sendCommand(Command.publicMessageCommand(message));
    }

    // метод по отправке команды авторизации
    public void sendAuthMessage(String login, String password) throws IOException {
        sendCommand(Command.authCommand(login, password));
    }

    // создаем функционал по получению данных

    public Thread startReadMessageProcess() {                  // метод будет принимать некий обработчик сообщений
        //ожидаем сообщение в отдельном потоке, в фоне поэтому поок служебный

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {                                                   // бесконечно ожидаем сообщения, читаем сообщение
                    try {
                        if (Thread.currentThread().isInterrupted()) {            // если теущий поток прерван
                            return;                                              // выходим из метода
                        }
                        Command command = readCommand();


                        // после получения сообщения будет пробегать по коллекции обработчиков команд

                        for (ReadMessageListener listener : listeners) {
                            listener.processReceivedCommand(command);       // у каждого обработчика будем вызывать метод по обработке команды
                        }
                    } catch (IOException e) {
                        System.err.println("Не удалось получить сообщение от сервера");
                        e.printStackTrace();
                        close();
                        break;
                    }
                }
            }
        });
        thread.setDaemon(true);                                                 // поток будет служебным
        thread.start();
        return thread;
    }

    // метод по добавлению обработчиков в коллекцию
    public ReadMessageListener addReadMessageListener(ReadMessageListener listener) {
        this.listeners.add(listener);   //добавляем
        return listener;                // и возвращаем
    }

    // метод по удалению обработчиков в коллекцию
    public void removeReadMessageListener(ReadMessageListener listener) {
        this.listeners.remove(listener);
    }

    //метод по закрытию сетевого соединения
    public void close() {                                                       // отключение от сервера
        try {
            connected = false;
            socket.close();                                                     //метод close закрывает socket клиентский который подключен к серверу
            readMessageProcess.isInterrupted();                                 // прерываем поток
        } catch (IOException e) {
            System.err.println("Не удалось закрыть сетевое соединение");
        }
    }

    public boolean isConnected() {
        return connected;
    }
}
