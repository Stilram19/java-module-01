package ex04;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService(new UsersArrayList());

        // Add users
        service.addUser(1000, "Alice");
        service.addUser(500, "Bob");
        service.addUser(2000, "Charlie");

        // Get user balances
        System.out.println("Alice Balance: " + service.getUserBalance(1)); // 1000
        System.out.println("Bob Balance: " + service.getUserBalance(2));   // 500

        // Perform a valid transfer
        service.transfer(1, 2, 200); // Alice sends 200 to Bob

        // Check balances after transfer
        System.out.println("Alice Balance: " + service.getUserBalance(1)); // 800
        System.out.println("Bob Balance: " + service.getUserBalance(2));   // 700

        // Perform an invalid transfer (Insufficient funds)
        try {
            service.transfer(2, 3, 1000); // Bob tries to send 1000 to Charlie (should fail)
        } catch (IllegalTransactionException e) {
            System.out.println("Exception: " + e.getMessage()); // Expected output
        }

        // Display user transactions
        System.out.println("\nAlice Transactions:");
        for (Transaction t : service.getUserTransfers(1)) {
            System.out.println(t);
        }

        System.out.println("\nBob Transactions:");
        for (Transaction t : service.getUserTransfers(2)) {
            System.out.println(t);
        }

        // Remove a transaction (example: Alice wants to remove a specific transaction)
        String transactionIdToRemove = service.getUserTransfers(1)[0].getIdentifier(); // Get Alice's first transaction ID
        service.removeTransaction(1, transactionIdToRemove);
        System.out.println("\nAfter removing transaction from Alice:");

        for (Transaction t : service.getUserTransfers(1)) {
            System.out.println(t);
        }

        // Check for invalid transactions
        Transaction[] unpairedTransactions = service.checkTransactionsValidity();
        System.out.println("\nUnpaired Transactions:");
        for (Transaction t : unpairedTransactions) {
            System.out.println(t);
        }
    }
}
