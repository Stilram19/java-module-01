package ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        TransactionsLinkedList transactionsList1 = new TransactionsLinkedList();
        TransactionsLinkedList transactionsList2 = new TransactionsLinkedList();
        User user1 = new User(100.0, "Omar", transactionsList1);
        User user2 = new User(100.0, "Ayoub", transactionsList2);

        Transaction t1 = new Transaction(TransactionType.DEBIT, 20, user1, user2);
        Transaction t2 = new Transaction(TransactionType.DEBIT, 20, user2, user1);
        Transaction t3 = new Transaction(TransactionType.DEBIT, 30, user1, user2);

        transactionsList1.addTransaction(t1);
        transactionsList1.addTransaction(t2);
        transactionsList1.addTransaction(t3);

        Transaction[] transactionsArray = transactionsList1.toArray();
        System.out.println("Transactions in list:");
        for (Transaction t : transactionsArray) {
            System.out.println(t);
        }

        transactionsList1.removeTransaction(t2.getIdentifier());
        transactionsArray = transactionsList1.toArray();
        System.out.println("\nAfter removing t2:");
        for (Transaction t : transactionsArray) {
            System.out.println(t);
        }

        try {
            transactionsList1.removeTransaction(UUID.randomUUID().toString());
        } catch (TransactionNotFoundException e) {
            System.out.println("\nExpected Exception: " + e.getMessage());
        }
    }
}
