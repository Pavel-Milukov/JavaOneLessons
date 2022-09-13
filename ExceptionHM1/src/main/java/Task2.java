public class Task2 {

    public static void main(String[] args) {

    }

    //  Посмотрите на код, и подумайте сколько разных типов исключений вы тут сможете получить?
    public static int sum2d(String[][] arr) {
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < 5; j++) {
             int val = Integer.parseInt(arr[i][j]);
             sum += val;
        }
    }
     return sum;
    }

    /*
    На 12-ой строке кода возможно генерирование исключения типа NumberFormatException, а также ArrayIndexOutOfВoundsException.
    При вызове метода возможна исключение типа NullPointerException.
    */
}
