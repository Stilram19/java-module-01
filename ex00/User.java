package ex00;

public class User {
    // fields
    int     id; // user ID generation logic is beyond the scoop of this exercise
    double  balance;
    String  name;

    public User(int balance, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance of a user cannot be negative");
        }

        this.balance = balance;
        this.name = name;
    }
}
