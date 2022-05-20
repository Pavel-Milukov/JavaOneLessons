package com.geekbrains.clientchat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class NetWork {  // умеет подключаться к серверу,здесь будем описывать подключение к серверу, логику по отправке данных на сервер и логику по чтению данных из сервера, будет
                        // использоваться с классом ClientChat работа с сетевыми соединениями

    private static NetWork INSTANCE;    // релизуем сущность NetWork чтобы во всех упоминаиях о ней присутствовал один и тот же экземпляр данной сущности, чтобы не создали несколько классво подключений

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 12_001;


    private int port;
    private String host;

    private Socket socket;
    private DataInputStream socketInput;                   // потоки запоминаются в поле класса
    private DataOutputStream socketOutput;                 // потоки запоминаются в поле класса

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

            // Создаем потоки на ввод и на вывод
            socketInput = new DataInputStream(socket.getInputStream());                   //чтение, инициализируем потоки
            socketOutput = new DataOutputStream(socket.getOutputStream());                //запись, инициализируем потоки

            return true;                                    // если удалось подключиться, то возвращаем истина
        } catch (IOException e) {
            e.printStackTrace();
            return false;                                    //если не удалось подключиться то возвращаем ложь
        }
    }

    // создаем функционал по отправке сообщений на сервер по сети

    public void sendMessage(String message) throws IOException {               // сообщение в аргументе, использует поток на запись socketOutput
        try {
            socketOutput.writeUTF(message);                     // отправим сообщение по сети
        } catch (IOException e) {
            System.err.println("Не удалось отправить сообщение на сервер");
            e.printStackTrace();
            throw e;
        }
    }

    // создаем функционал по получению данных

    public void waitMessages(Consumer<String> messageHandler) {                  // метод будет принимать некий обработчик сообщений
        //ожидаем сообщение в отдельном потоке, в фоне поэтому поок служебный

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {                                                   // бесконечно ожидаем сообщения, читаем сообщение
                    try {
                        if (Thread.currentThread().isInterrupted()) {            // если теущий поток прерван
                            return;                                              // выходим из метода
                        }
                        String message = socketInput.readUTF();                  // с помощью socketInput ожидаем сообщение
                        messageHandler.accept(message);                          // метод accept будет вызываться когда сообщение получено
                    } catch (IOException e) {
                        System.err.println("Не удалось получить сообщение от сервера");
                        e.printStackTrace();
                        break;
                    }
                }
            }
        });
        thread.setDaemon(true);                                                 // поток будет служебным
        thread.start();
    }

    //метод по закрытию сетевого соединения
    public void close() {                                                       // отключение от сервера
        try {
            socket.close();                                                     //метод close закрывает socket клиентский который подключен к серверу
        } catch (IOException e) {
            System.err.println("Не удалось закрыть сетевое соединение");
        }
    }
}
