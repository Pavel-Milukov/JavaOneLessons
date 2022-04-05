import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Example2 extends JFrame {

    public Example2() {
        setTitle("Second window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // приложение будет останавливать свою работу при нажатии
        setBounds(500,500,640,480);           // задает координаты окна:от верхней левой часть окна отступаем, далее размер окна

        JButton [] buttons = new JButton[5];                   // создали массив, в нем кнопки
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("#" + i);            // создаем кнопки, но пока без добавления
            buttons[i].addActionListener(new ActionListener() {  //прослушивание каких-то действий через анонимный класс, метод принимает интерфейс
                @Override
                public void actionPerformed(ActionEvent e) {     // задаем реализацию метода, в методе ActionEvent
                    System.out.println("Hello from" + ((JButton)e.getSource()).getText());          // когда мы нажали произошло событие, а событие мы слушаем ( в методе ActionEvent и оно отработало в addActionListener - обработчик нажатия)
                }
            });
        }

        add(buttons[0], BorderLayout.EAST);                  // добавляем и располагаем кнопку
        add(buttons[1], BorderLayout.WEST);
        add(buttons[2], BorderLayout.SOUTH);
        add(buttons[3], BorderLayout.NORTH);
        add(buttons[4], BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String [] args) {
        new Example2();
    }
}
