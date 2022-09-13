public class Task1 {

    public static void main (String [] args) {

        negativeSize();
        nullPointer();
        numberFormat();
    }

    public static void negativeSize () {
        int [] array = new int [-5];
    }

    public static void nullPointer () {
        String [] array = null;
        int a = array.length;
    }
    public static void numberFormat () {
        int a = Integer.parseInt("a");
        System.out.println(a);
    }
}
