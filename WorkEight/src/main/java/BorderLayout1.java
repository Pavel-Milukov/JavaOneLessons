import javax.swing.*;
import java.awt.*;

public class BorderLayout1 extends JFrame {

    public BorderLayout1() {
        setTitle("First window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // приложение будет останавливать свою работу при нажатии
        setBounds(500,500,640,480);           // задает координаты окна:от верхней левой часть окна отступаем, далее размер окна

        JButton button = new JButton("Button 1 (PAGE_START)");
        add(button, BorderLayout.PAGE_START);                 // добавляем кнопку

        button = new JButton("Button 2 (CENTER)");
        add(button, BorderLayout.CENTER);

        button = new JButton("Button 3 (LINE_START)");
        button.setSize(new Dimension(200,200));
        add(button, BorderLayout.LINE_START);

        button = new JButton("Long-Named Button 4 (PAGE_END)");
        add(button, BorderLayout.PAGE_END);

        button = new JButton("5 (LINE_END)");
        add(button, BorderLayout.LINE_END);

        setVisible(true);
    }

    public static void main ( String[] args) {
        new BorderLayout1();
    }
}
