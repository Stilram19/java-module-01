package ex04;

class TransactionsService {
    UsersList users;

    void addUser(double balance, String name) {
        User newUser = new User(balance, name, new TransactionsLinkedList());

        users.addUser(newUser);
    }

    double getUserBalance(int userId) {
        return (users.getUserById(userId).getBlance());
    }

    void transfer(int senderId, int receiverId, double amount) {}
}
