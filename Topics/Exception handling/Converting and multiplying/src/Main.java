import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> ar = new ArrayList<>();
        while (scanner.hasNext()) {
            ar.add(scanner.nextLine());
        }
        for (String s : ar) {
            try {
                int n = Integer.parseInt(s);
                if (n != 0) {
                    System.out.println(n * 10);
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid user input: " + s);
            }
        }
    }
}