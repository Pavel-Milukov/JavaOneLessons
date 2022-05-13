import java.io.*;
import java.net.ServerSocket;   // здесь класс для создания сервера, обрабатывающий клиенские подключения
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {       // наш сервер будет принимать только одно подключение

    private static final int PORT = 12_001;   // наш порт сервера

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {  // класс предназначен для создание сервера когда у нас классы реализуют интерфейсы Autocloseable,
                                                                    // то мы можем создавать экз. данных классов в конструкции try с ресурсами. Если не создаться сокет то будет выброшено исключение

            System.out.println("Сервер начал работу, ожидаем новое подключение");                // для логирования

            // у класса серверного сокета есть метод по ПРИЕМУ подключений, принимает подключение которое пришло на сервер и возвращает экз. сокету

            Socket clientSocket = serverSocket.accept();           // метод по приему подключений, принимает подключение, которое пришло на сервер, точка соединения с клиентом который к нам подключился
                                                                   // на сервер, его (IP-адрес и порт) запишется в объект типа Socket, результат этого метода кладем в переменную clientSocket
                                                                   // метод .accept() блокирует код пока не будет создано подключения ( в Java Doc есть инфа )
            System.out.println("Клиент подключился");             // для логирования

            // разница InputStream от DataInputStream: InputStream  читает информацию побайтово методы только по чтению byte, когда мы переадем строки/числа то читать побайтово неудобно
            // поэтому взяли некий класс обертку DataInputStream который умеет на основе байтового потока принимать и читать информацию с типизацией

            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());// у клиенского сокета вызовем поток на ожидание данных которые приходят по этому клиентскому сокету ЧТЕНИЕ
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream()); // ЗАПИСЬ

            processClientConnect(inputStream, outputStream);




        } catch (IOException e) {                                                // обрабатываем исключение, когда мы не смогли открыть соединение по этому сокету.

            // чтобы эта ошибка подсвечивалась красным, нужно сделать ПОТОК вывода err, он находся в классе System, является PrintStream и выводит сообщение,
            // данный поток отвечает за поток ошибок, все ошибки которые подсвечиваются красным они идут в потоке Error
            System.err.println("Ошибка при подключении к порту " + PORT);
            e.printStackTrace();

        }
    }

    public static void processClientConnect(DataInputStream inputStream, DataOutputStream outputStream) {

        // будем ожидать из потока на чтение с помощью метода .readUTF (UTF - кодировка), можем читать строку которую передали в данном потоке, МЫ ПЕРЕДАЕМ ТОЛЬКО ТЕКСТ!!!
        // поток на чтение информации clientSocket.getInputStream(), изначально принимаем БАЙТЫ, потом обарачиваем в new DataInputStream, где нам этот класс позволяет читать не только байты
        // а сразу строку

        new Thread(new Runnable() {                 //Поток для чтения входящих сообщений
            @Override
            public void run() {
                try {
                    while (true) {
                        String comingMessage = inputStream.readUTF();
                        System.out.println(comingMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {                  // Поток на запись введенного через консоль сообщения
            @Override
            public void run() {
                try {
                    while (true) {
                        Scanner scanner = new Scanner(System.in);
                        String outMessage = scanner.nextLine();
                        outputStream.writeUTF("Сообщение от сервера: " + outMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
