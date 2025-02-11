package ex03;

// unchecked exception (extends RuntimeException)
public class TransactionNotFoundException extends RuntimeException {
    TransactionNotFoundException() {
        super("transaction not found!");
    }

    TransactionNotFoundException(String cause) {
        super("transaction not found: " + cause);
    }
}
