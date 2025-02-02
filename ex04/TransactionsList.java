package ex04;

interface TransactionsList {
    void addTransaction(Transaction newTransaction);
    void removeTransaction(String uuid);
    Transaction[] toArray();
}
