package ex04;

class TransactionNode {
    public Transaction transaction;
    public TransactionNode next;

    TransactionNode(Transaction transaction, TransactionNode next) {
        if (transaction == null) {
            throw new IllegalArgumentException("TransactionNode cannot be constructed with a null transaction");
        }
        this.transaction = transaction;
        this.next = next;
    }
}
