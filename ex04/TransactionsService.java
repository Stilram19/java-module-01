package ex04;

class TransactionsService {
    UsersList users;

    private enum UserTransferRole {
        RECIPIENT,
        SENDER;
    }

    TransactionsService(UsersList users) {
        if (users == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }

        this.users = users;
    }

    void addUser(double balance, String name) {
        User newUser = new User(balance, name, new TransactionsLinkedList());

        users.addUser(newUser);
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
        Transaction receiverTransaction = new Transaction(TransactionType.DEBIT, amount, sender, receiver);
        Transaction senderTransaction = new Transaction(TransactionType.CREDIT, -1 * amount, sender, receiver);

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
            for (int j = 0; j < currUserTransactions.length; j++) {
                boolean isCurrTransactionPaired = false;
                UserTransferRole userRole = currUserTransactions[j].getSender() == currUser ? UserTransferRole.SENDER : UserTransferRole.RECIPIENT;
                User otherUser = userRole == UserTransferRole.RECIPIENT ? currUserTransactions[j].getRecipient() : currUserTransactions[j].getSender();
                Transaction[] otherUserTransactions = this.getUserTransfers(otherUser.getId());
                for (int k = 0; k < otherUserTransactions.length; k++) {
                    if (((currUser == otherUserTransactions[k].getRecipient() && userRole == UserTransferRole.RECIPIENT)
                        || (currUser == otherUserTransactions[k].getSender() && userRole == UserTransferRole.SENDER))
                        && otherUserTransactions[k].getTransferAmount() == -currUserTransactions[j].getTransferAmount()) {
                        isCurrTransactionPaired = true;
                        break ;
                    }
                }

                if (isCurrTransactionPaired == false) {
                    unpairedTransactions.addTransaction(currUserTransactions[j]);
                }
            }
        }

        return unpairedTransactions.toArray();
    }
}
