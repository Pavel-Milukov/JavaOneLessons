import javax.swing.*;     // подгрузили библиотеку для работы

public class Example1 extends JFrame {

    public Example1() {
        setTitle("First window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // приложение будет останавливать свою работу при нажатии
        setBounds(200,600,300,300);           // задает координаты окна:от верхней левой часть окна отступаем, далее размер окна
        setVisible(true);
    }

    public static void main ( String[] args) {
        new Example1();
    }
}
