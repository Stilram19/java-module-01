package ex00;

public class Program {
    public static void main(String[] args) {
        try {
            User user1 = new User(50, "Peter");
            User user2 = new User(100, "Nicolas");

            System.out.println("user1: " + user1.toString());
            System.out.println("user2: " + user2.toString());

            Transaction transaction1 = new Transaction(TransactionType.DEBIT, 30, user2, user1);
            Transaction transaction2 = new Transaction(TransactionType.CREDIT, -30, user2, user1);

            System.out.println("transaction1: " + transaction1.toString());
            System.out.println("transaction2: " + transaction2.toString());

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument!");
        }
    }
}
