

//                        НАПИСАНИЕ ПРОГРАММЫ КРЕСТИКИ НОЛИКИ ПК VS ЧЕЛОВЕК В ПРОЦЕДУРНОМ СТИЛЕ

import java.util.Random;
import java.util.Scanner;

public class HomeWorkFour {

    private static char [][] map;                               // Сперва нужно поле для игры - создаем двумерный массив он не инициализирован (пустой)
    private final static int MAP_SIZE = 5;                      // Размер поля кол-во клеток - здесь переменная константа
    private final static int DOTS_COUNT_TO_WIN = 4;             // число клеток при достижении которых происходит победа

    private final static char DOT_X = 'X';                      // Символ X для использования в игре
    private final static char DOT_O = 'O';                      // Символ O для использования в игре
    private final static char DOT_EMPTY = '•';                  // Символ • для заполнения клетки в начале по логике для поля
    private static Scanner sc = new Scanner(System.in);   // вводим сканер для ввода данных игроком
    private static Random random = new Random();



    public static void main (String[] args) {
        play();
    }


    private static void createField() {        // метод создания игрового поля
        map = new char[MAP_SIZE][MAP_SIZE];    // Инициализиурем массив - само игровое поле размером константы MAP_SIZE
        for (int i = 0; i < map.length; i++) { // В начале поле должно быть без крестиков и ноликов заполняем пустыми символами
            for (int j = 0; j < map[i].length; j++) {
                map [i][j] = DOT_EMPTY;
            }
        }
    }

    private static void print() {               // Метод печати игрового поля для отображения игрокам до и после ходов
        for (int i=0;i < map.length +1; i++) {  //  печать горизонтальной шапки ( не массива )
            if (i == 0) {                       // Проверка для нулевого значения чтобы напечатать пробел в левом углу
                System.out.print("  ");         //два пробела
            } else {
                System.out.print((i) + " ");
            }
        }
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.print((i+1) + " ");      //  печать номера для вертикального номера печатаем просто цифру
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean cellValidation(int x, int y) {        // проверка поля если прошло ход разрешается - валидация самой клетки ( человек и пк)
        if (x < 1 || x > MAP_SIZE || y < 1 || y > MAP_SIZE) {   // первая валидация: проверка что коордианты не выходят за рамки игрового поля (массива)
            System.out.println("Exit map sizes");
            return false;
        }
        boolean check = map[x -1][y -1] == DOT_EMPTY;  // вторая валидация: проверяется занята ли клетка
                                                        // вычитаем -1 потому что мы зависим от индекса массива - нашего игрового поля
        if (check) {  // здесь сама проверка на пустая ли клетка или здесь стоит Х или О
            return check;
        } else {
            System.out.println("Cell is Busy");
            return false;
        }
    }

    private static void humanTurn() {                                  // метод хода человека
        int x,y;
        do {                          // человеку нужно сделать ход - он проверяется
            while (true) {            // проверка на ввод наших цифр что они как данные верны для программы с консоли
                System.out.println("Please input dots coordinate in format 'x y'");
                if (sc.hasNextInt()) {   // при вводе первой координаты продолжай код
                    x = sc.nextInt();
                } else {
                    System.out.println("You input wrong  X coordinate format");
                    sc.nextLine();
                    continue;        // если ошибка заного повторяем
                }

                if (sc.hasNextInt()) {
                    y = sc.nextInt();
                    break;
                } else {
                    System.out.println("You input wrong  Y coordinate format");
                    sc.nextLine();
                }
            }
        } while (!cellValidation(x, y)); // если будет true от метода проверки то у нас больше в цикл do while не зайдет
        map[x - 1][y - 1] = DOT_X;               // главное после всех проверок данные записать
    }

    private static void computerTurn() {      // метод хода пк
        System.out.println("Computer turn");
        int x,y;
        do {
            x = random.nextInt(MAP_SIZE);
            y = random.nextInt(MAP_SIZE);
        } while (!cellValidation(x +1, y +1)); // так как в валидации отнимаем нужно прибавить для пк +1
        map[x][y] = DOT_O;                // главное после всех проверок данные записать
    }

    private static boolean checkDraw() {                   // проверка на ничью
        for (int i = 0; i < map.length;i++){               // есть ли поле где символ = пустое поле
            for (int j = 0; j < map[i].length;j++) {
                if (map[i][j] == DOT_EMPTY) {       // если условие вернуло true то значит пустое поле еще есть - значит возвращаем false на наш метод что ничьи нет
                    return false;
                }
            }
        }
        return  true;
    }

    private static boolean checkWin(char dot, int countWin) {              // проверка на победу
        //проверка по строчкам

        for (int i = 0; i < map.length;i++) {
            int sum = 0;
            for (int y = 0; y < map[i].length;y++) {
                if (map[i][y] == dot) {
                    sum++;
                }
                if (sum== countWin) {
                    return true;
                }
            }
        }


        //проверка по колонкам

        for (int i = 0; i < map.length; i++) {
            int sum = 0;
            for (int y = 0; y < map[i].length; y++) {
                if (map[y][i] == dot) {
                    sum ++;
                }
                if (sum == countWin) {
                    return true;
                }
            }
        }

        //проверка по  левой диоганале

        for (int i = 0; i < 1; i++) {
            int sum = 0;
            for (int y = 0; y < map.length; y++) {
                if (map[y][y] == dot) {
                    sum++;
                }
            }
            if (sum == countWin) {
                return true;
            }
        }

        //проверка по  правой диоганале
        for ( int i = 0; i < 1; i++) {
            int sum = 0;
            for ( int y = 0; y < map.length; y++) {
                if (map[y][(map[y].length) - 1 - y] == dot) {
                    sum++;
                }
            }
            if ( sum == countWin) {
                return true;
            }
        }

        return false;
    }

    private static void play() {           // метод самой игры
        createField();
        print();
        while (true) {                     // будут повторятся методы пока логически все не походят и будет закончена игра победой человека, пк или ничьей
            humanTurn();
            print();
            if (checkWin(DOT_X, DOTS_COUNT_TO_WIN)) {         // Сначала проверка на победу человека,если проверка показала что символы заполнены выносим сообщение и игра заканчивается
                System.out.println("You win");
                break;
            }
            if (checkDraw()) {
                System.out.println("Draw");
                break;
            }

            computerTurn();
            print();
            if (checkWin(DOT_O, DOTS_COUNT_TO_WIN)) {              // Сначала проверка на победу пк,если проверка показала что символы заполнены выносим сообщение и игра заканчивается
                System.out.println("Computer win"); // меняем строку чтобы отличать, кто выиграл
                break;
            }
            if (checkDraw()) {
                System.out.println("Draw");
                break;
            }
        }
    }

}

