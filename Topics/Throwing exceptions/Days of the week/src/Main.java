import java.util.*;

public class Main {

    public static String getDayOfWeekName(int number) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "Mon");
        map.put(2, "Tue");
        map.put(3, "Wed");
        map.put(4, "Thu");
        map.put(5, "Fri");
        map.put(6, "Sat");
        map.put(7, "Sun");

        if (map.containsKey(number)) {
            return map.get(number);
        } else {
            throw new IllegalArgumentException("java.lang.IllegalArgumentException");
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayNumber = scanner.nextInt();
        try {
            System.out.println(getDayOfWeekName(dayNumber));
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
}