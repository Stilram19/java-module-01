package ex02;

public class User {
    // fields
    private final int       id; // user ID generation logic is beyond the scoop of this exercise
    private final String    name;
    private double          balance;

    // constructor
    public User(double balance, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance of a user cannot be negative");
        }

        this.id = UserIdsGenerator.getInstance().generateId();
        this.balance = balance;
        this.name = name;
    }

    // getters
    public int getId() {
        return (id);
    }

    public double getBlance() {
        return (balance);
    }

    public String getName() {
        return (name);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds. Available balance: " + this.balance);
        }

        this.balance -= amount;
    }

    @Override
    public String toString() {
        return String.format("User[ID: %d, Name: %s, Balance: %.2f]", id, name, balance);
    }
}
