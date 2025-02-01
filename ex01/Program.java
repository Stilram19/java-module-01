package ex01;

public class Program {
    public static void main(String[] args) {
        User user1 = new User(100, "Omar");
        User user2 = new User(100, "Ayoub");
        User user3 = new User(100, "Mouad");

        System.out.println("user1: " + user1.toString());
        System.out.println("user2: " + user2.toString());
        System.out.println("user3: " + user3.toString());
    }
}
