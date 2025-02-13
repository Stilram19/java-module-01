package ex05;

// unchecked exception (extends RuntimeException)
public class IllegalTransactionException extends RuntimeException {
    IllegalTransactionException() {
        super("illegal transaction!");
    }

    IllegalTransactionException(String cause) {
        super("illegal transaction: " + cause);
    }
}
