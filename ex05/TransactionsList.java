package ex05;

interface TransactionsList {
    void addTransaction(Transaction newTransaction);
    Transaction removeTransaction(String uuid);
    Transaction[] toArray();
}
