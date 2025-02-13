package ex05;

class TransactionNotFoundException extends RuntimeException {
    TransactionNotFoundException() {
        super("transaction not found!");
    }

    TransactionNotFoundException(String cause) {
        super("transaction not found: " + cause);
    }
}
