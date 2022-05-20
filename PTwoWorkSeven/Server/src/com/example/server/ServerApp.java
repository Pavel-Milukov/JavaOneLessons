package com.example.server;

import com.example.server.chat.MyServer;

public class ServerApp {                        // эта сущность для: запуск сервера отдельно

    private static final int PORT = 12_001;     // константа, порт который будет занимать сервер

    public static void main (String [] args) {
        new MyServer().start(PORT);             // инициализируем объект, вызываем у него метод start с аргументом порта нашего сервера
    }
}
