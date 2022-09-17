import java.util.Scanner;

public class Task4 {

    public static void main(String[] args) {
        System.out.println("You entered: " + stringChecker());
    }

    public static String stringChecker () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a word");
        String value = null;
        try {
            value = scanner.nextLine();
            if (value.isEmpty()) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("You cant enter an empty string");
            return "Empty string";
        }
        return value;
    }
}
