public class HomeWorkTwo {

    public static void main (String [] args) {

        System.out.println (checkSum(7,4));
        checkNumber(0);
        System.out.println(checkNegativeNumber(-7));
        printWord("Test of method", 5);
        System.out.println(checkYear(2022));
    }


    // Задание № 1

    public static boolean checkSum ( int a, int b) {
        if ( (a + b >= 10) && (a+b <= 20) ) {
            return true;
        } else {
            return false;
        }
    }

    // Задание № 2

    public static void checkNumber ( int c) {
        if ( c > -1) {
            System.out.println ("Число положительное");
        } else {
            System.out.println ("Число отрицательное");
        }
    }

    // Задание № 3

    public static boolean checkNegativeNumber (int d) {
        if (d < 0) {
            return true;
        } else {
            return false;
        }
    }

    // Задание № 4

    public static void printWord (String Word, int e) {
        for (int i = 0; i < e; i++) {
            System.out.println(Word);
        }
    }

    // Задание № 5

    public static boolean checkYear ( int year ) {
        if (year % 400 == 0) {             // Первое: проверяем каждый 400-ый год
            return true;
        } else if ( year % 100 == 0) {     // Второе: отсеиваем каждый 100-ый год
            return false;
        } else if ( year % 4 == 0) {       // Третье: отфильтровав первые 2 отбора, фильтруем по последней проверке - каждый 4-ый год
            return true;
        } else {                           // Четвертое: Все оставшиеся варианты являются не високосными
            return false;
        }
    }
}
