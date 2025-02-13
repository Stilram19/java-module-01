package ex04;

class TransactionsService {
    UsersList users;

    // constructor's dependency injection
    TransactionsService(UsersList users) {
        if (users == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }

        this.users = users;
    }

    User addUser(double balance, String name) {
        User newUser = new User(balance, name, new TransactionsLinkedList());
        users.addUser(newUser);
        return (newUser);
    }

    double getUserBalance(int userId) {
        return (users.getUserById(userId).getBalance());
    }

    void transfer(int senderId, int receiverId, double amount) {
        User sender = this.users.getUserById(senderId);

        if (sender.getBalance() < amount) {
            throw new IllegalTransactionException("Insufficient funds. Available balance: " + sender.getBalance());
        }

        User receiver = this.users.getUserById(receiverId);
        TransactionsList receiverTransactionsList = receiver.getTransactions();
        TransactionsList senderTransactionsList = sender.getTransactions();

        // creating two transactions DEBIT/CREDIT with the same ID 
        Transaction receiverTransaction = new Transaction(TransactionType.DEBIT, amount, sender, receiver, null);
        Transaction senderTransaction = new Transaction(TransactionType.CREDIT, -amount, sender, receiver, receiverTransaction.getIdentifier());

        receiverTransactionsList.addTransaction(receiverTransaction);
        senderTransactionsList.addTransaction(senderTransaction);
    }

    Transaction[] getUserTransfers(int userId) {
        return (this.users.getUserById(userId).getTransactions().toArray());
    }

    void removeTransaction(int userId, String transactionUuid) {
        this.users.getUserById(userId).getTransactions().removeTransaction(transactionUuid);
    }

    Transaction[] checkTransactionsValidity() {
        int numberOfUsers = this.users.getNumberOfUsers();
        TransactionsList unpairedTransactions = new TransactionsLinkedList();

        for (int i = 0; i < numberOfUsers; i++) {
            User currUser = this.users.getUserByIndex(i);
            Transaction[] currUserTransactions = this.getUserTransfers(currUser.getId());
            for (Transaction currUserTransaction : currUserTransactions) {
                boolean isCurrTransactionPaired = false;
                User recipient = currUserTransaction.getRecipient();
                User sender = currUserTransaction.getSender();
                User otherUser = currUser == recipient ? sender : recipient;
                Transaction[] otherUserTransactions = this.getUserTransfers(otherUser.getId());
                for (Transaction otherUserTransaction : otherUserTransactions) {
                    if (currUserTransaction.getIdentifier().equals(otherUserTransaction.getIdentifier())) {
                        isCurrTransactionPaired = true;
                        break ;
                    }
                }

                if (isCurrTransactionPaired == false) {
                    unpairedTransactions.addTransaction(currUserTransaction);
                }
            }
        }

        return unpairedTransactions.toArray();
    }
}
