package ex03;

public class User {
    // fields
    private int                     id; // user ID generation logic is beyond the scoop of this exercise
    private final String            name;
    private double                  balance;
    private final TransactionsList  transactions;

    // constructor
    public User(double balance, String name, TransactionsList transactions) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        if (transactions == null) {
            throw new IllegalArgumentException("TransactionsList cannot be null");
        }

        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance of a user cannot be negative");
        }

        this.balance = balance;
        this.name = name;
        this.transactions = transactions; // using dependency injection
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

    @Override
    public String toString() {
        return String.format("User[ID: %d, Name: %s, Balance: %.2f]", id, name, balance);
    }
}
