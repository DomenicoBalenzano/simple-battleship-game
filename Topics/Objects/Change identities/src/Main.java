class Person {
    String name;
    int age;
}

class MakingChanges {
    public static void changeIdentities(Person p1, Person p2) {
        String p1name = p1.name;
        int p1age = p1.age;
        p1.age = p2.age;;
        p1.name = p2.name;
        p2.name = p1name;
        p2.age = p1age;

    }
}