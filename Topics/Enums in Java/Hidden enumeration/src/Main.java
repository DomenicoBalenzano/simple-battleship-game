public class Main {

    public static void main(String[] args) {
        int counter = 0;

        // write your code here
        for (Secret s : Secret.values()) {
            if (s.name().startsWith("STAR")) {
                counter++;
            }
        }


        System.out.println(counter);
    }
}


/*enum Secret {
    STAR, CRASH, START, // ...
}*/
