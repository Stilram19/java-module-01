package ex04;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService(new UsersArrayList());

        // add users
        service.addUser(1000, "Alice");
        service.addUser(500, "Bob");
        service.addUser(2000, "Charlie");

        // Get user balances
        System.out.println("Alice Balance: " + service.getUserBalance(1)); // 1000
        System.out.println("Bob Balance: " + service.getUserBalance(2));   // 500

        // perform a valid transfer
        service.transfer(1, 2, 200); // Alice sends 200 to Bob

        // check balances after transfer
        System.out.println("Alice Balance: " + service.getUserBalance(1)); // 800
        System.out.println("Bob Balance: " + service.getUserBalance(2));   // 700

        // perform an invalid transfer (Insufficient funds)
        try {
            service.transfer(2, 3, 1000); // Bob tries to send 1000 to Charlie (should fail)
        } catch (IllegalTransactionException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // display user transactions
        System.out.println("\nAlice Transactions:");
        for (Transaction t : service.getUserTransfers(1)) {
            System.out.println(t);
        }

        System.out.println("\nBob Transactions:");
        for (Transaction t : service.getUserTransfers(2)) {
            System.out.println(t);
        }

        // remove a transaction
        String transactionIdToRemove = service.getUserTransfers(1)[0].getIdentifier(); // Get Alice's first transaction ID
        service.removeTransaction(1, transactionIdToRemove);
        System.out.println("\nAfter removing transaction from Alice:");

        for (Transaction t : service.getUserTransfers(1)) {
            System.out.println(t);
        }

        // check for invalid transactions
        Transaction[] unpairedTransactions = service.checkTransactionsValidity();
        System.out.println("\nUnpaired Transactions:");
        for (Transaction t : unpairedTransactions) {
            System.out.println(t);
        }

        // perform another transfer
        service.transfer(3, 2, 1200); // Charlie sends 1200 to Bob
        // check balances after transfer
        System.out.println("\nBob Balance: " + service.getUserBalance(2)); // 1900
        System.out.println("Charlie Balance: " + service.getUserBalance(3));   // 800

        // remove a transaction
        String transactionIdToRemove2 = service.getUserTransfers(2)[1].getIdentifier(); // Get Bob's second transaction ID
        service.removeTransaction(2, transactionIdToRemove2);
        System.out.println("\nAfter removing transaction from Bob:");

        for (Transaction t : service.getUserTransfers(1)) {
            System.out.println(t);
        }

        // check for invalid transactions
        Transaction[] unpairedTransactions1 = service.checkTransactionsValidity();
        System.out.println("\nUnpaired Transactions:");
        for (Transaction t : unpairedTransactions1) {
            System.out.println(t);
        }
    }
}
