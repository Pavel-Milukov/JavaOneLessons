import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 12_001);                   // создаем сокет,указываем адрес и порт нашего сервера, инициализируем подключение к серверу

        //Эти потоки ввода-вывода используются для передачи и приема данных.

        DataInputStream in = new DataInputStream(socket.getInputStream());           //у сокета вызовем поток на ожидание данных которые приходят по этому клиентскому сокету ЧТЕНИЕ
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());       // ЗАПИСЬ

        new Thread(new Runnable() {                                                  //Поток для чтения входящих сообщений
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = in.readUTF();
                        System.out.println(strFromServer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {                                                  // Поток на запись введенного через консоль сообщения
            @Override
            public void run() {
                while (true) {
                    Scanner sc = new Scanner(System.in);
                    String text = sc.nextLine();
                    try {
                        out.writeUTF("Сообщение от клиента: " + text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();




    }


}
