import java.util.Arrays;

public class Task4 {

    public static void main(String[] args) {

        int [] one = {12,20,14};
        int [] two = {2,6,4};
        int [] three = {2};
        int [] four = {1,6,0};
        System.out.println(Arrays.toString(divideMethod(one,four)));
    }

    public static int [] divideMethod (int [] a, int [] b) {
        if (a.length != b.length) {
            throw new RuntimeException("Sizes of arrays are not equal!");
        }
        int [] resultArray = new int [a.length];
        for (int i = 0; i < a.length; i++) {
            if (b[i] == 0) {
                throw new RuntimeException("Index of second array equals zero, we cant divide by zero!");
            }
            resultArray[i] = a[i] / b[i];
        }
        return resultArray;
    }
}
