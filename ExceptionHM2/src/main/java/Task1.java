import java.util.InputMismatchException;
import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        System.out.println(userEnterFloat());
    }

    public static float userEnterFloat () {
        float value = 0.0f;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter float value!");
        while (true){
            try {
                value = scanner.nextFloat();
                if ((value % 1) ==0 ) {
                    System.out.println("You enter not a float value! Try again!");
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("You enter wrong value! Try again!");
                String string = scanner.nextLine();
            }
        }
    }
}
