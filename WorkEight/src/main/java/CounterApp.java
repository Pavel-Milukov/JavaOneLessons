import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CounterApp extends JFrame {

    private int counter = 0;

    public CounterApp()  {
        setTitle("CounterApp");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // приложение будет останавливать свою работу при нажатии
        setBounds(500,500,640,480);             // задает координаты окна:от верхней левой часть окна отступаем, далее размер окна

        Font font = new Font("Arial", Font.BOLD, 30);   // редактируем текст


        JLabel counterView = new JLabel(String.valueOf(counter));  // Отображает текст
        counterView.setHorizontalAlignment(SwingConstants.CENTER); // установка положения самой цифры внутри кнопки
        counterView.setFont(font);                                 // после создания объекта font его используем через метод у объекта counterView
        add(counterView, BorderLayout.CENTER);

        JButton incrementButton = new JButton (">");           // создание кнопки увеличения занчения
        add(incrementButton, BorderLayout.LINE_END);                // добавляю кнопку
        incrementButton.addActionListener(new ActionListener() {    // обрабатываем нажатие на кнопку
            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;                                           // после увеличения нужно значение куда-то положить
                counterView.setText(String.valueOf(counter));        // у объекта counterView вызываем метод с установкой текста
            }
        });

        JButton decrementButton = new JButton("<");            // создание кнопки уменьшения значения
        add(decrementButton, BorderLayout.LINE_START);             // добавляю кнопку
        decrementButton.addActionListener(new ActionListener() {    // логика как с кнопкой incrementButton
            @Override
            public void actionPerformed(ActionEvent e) {
               counter--;
               counterView.setText(String.valueOf(counter));
            }
        });

        JButton resetScore = new JButton("Reset score");   // создание кнопки обнуления счетчика задание №1
        add(resetScore, BorderLayout.NORTH);
        resetScore.addActionListener(new ActionListener() {    // логика как с кнопкой incrementButton
            @Override
            public void actionPerformed(ActionEvent e) {
                counter = 0;                                   // значение приравниваем к 0
                counterView.setText(String.valueOf(counter));
            }
        });

        JButton increaseByTen = new JButton("Plus 10");
        add(increaseByTen, BorderLayout.SOUTH);
        increaseByTen.addActionListener(new ActionListener() {    // логика как с кнопкой incrementButton
            @Override
            public void actionPerformed(ActionEvent e) {
                counter += 10;                                   // значение приравниваем к 0
                counterView.setText(String.valueOf(counter));
            }
        });


        setVisible(true);
    }

    public static void main (String [] args) {
        new CounterApp();
    }

}
