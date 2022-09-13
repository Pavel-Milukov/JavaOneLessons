import java.util.Arrays;

public class Task3 {

    public static void main(String[] args) {
        int[] one = {7,6};
        int[] two = {6,5};
        int [] three = {1,3,5};
        System.out.println(Arrays.toString(subtractMethod(one,two)));
    }

    public static int [] subtractMethod (int [] a, int [] b) {
        if (a.length != b.length) {
            throw new RuntimeException("Array sizes are not equal");
        }
        int [] result = new int [a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }
}
