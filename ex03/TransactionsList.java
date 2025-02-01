package ex03;

public interface TransactionsList {
    void addTransaction(Transaction newTransaction);
    void removeTransaction(String uuid);
    Transaction[] toArray();
}
