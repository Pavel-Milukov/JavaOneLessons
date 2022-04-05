import javax.swing.*;
import java.awt.*;

public class BorderLayout2 extends JFrame {

    public BorderLayout2() {
        setTitle("First window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // приложение будет останавливать свою работу при нажатии
        setBounds(500,500,640,480);           // задает координаты окна:от верхней левой часть окна отступаем, далее размер окна

        JButton[] buttons = new JButton[10];
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS)); // ОСЬ,  используем компоновщик BoxLayout

        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            button = new JButton("#" + i);
            button.setPreferredSize(new Dimension(300, 200)); // размер кнопок
            button.setAlignmentX(Component.RIGHT_ALIGNMENT);   // расположение
            add(button);
        }

        setVisible(true);
    }

    public static void main ( String[] args) {
        new BorderLayout2();
    }
}
