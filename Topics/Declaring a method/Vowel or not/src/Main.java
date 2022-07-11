import java.util.Scanner;

public class Main {

    public static boolean isVowel(char ch) {
        char[] vowels = {'a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U'};
        boolean isVowel = false;
        for (char c : vowels) {
            if (ch == c) {
                isVowel = true;
                break;
            }
        }
        return isVowel;
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char letter = scanner.nextLine().charAt(0);
        System.out.println(isVowel(letter) ? "YES" : "NO");
    }
}