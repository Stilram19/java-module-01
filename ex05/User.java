package ex05;

class User {
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
        this.id = UserIdsGenerator.getInstance().generateId();
    }

    // getters
    public int getId() {
        return (id);
    }

    public double getBalance() {
        return (balance);
    }

    public String getName() {
        return (name);
    }

    public TransactionsList getTransactions() {
        return (this.transactions);
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
