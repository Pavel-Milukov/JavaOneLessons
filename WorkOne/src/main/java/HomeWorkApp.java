public class HomeWorkApp {

    public static void main(String[] args) {     // Задание № 1
        printThreeWords();
        checkSumSign();
        printColor();
        compareNumbers();
    }

    public static void printThreeWords() {       // Задание № 2
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }

    public static void checkSumSign() {          // Задание № 3
        int a = 3;
        int b = 7;
            if (a + b >= 0) {
                System.out.println("Сумма положительная");
            } else {
                System.out.println("Сумма отрицательная");
            }
    }

    public static void printColor() {           // Задание № 4
        int value = 100;
            if (value <= 0) {
                System.out.println("Красный");
            } else if (value > 100) {
                System.out.println("Зеленый");
            } else {
                System.out.println("Желтый");
            }
    }

    public static void compareNumbers() {       // Задание № 5
        int a = 30;
        int b = 40;
        if (a>=b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }

}
