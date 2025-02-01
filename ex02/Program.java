package ex02;

public class Program {
    public static void main(String[] args) {
        UsersList usersList = new UsersArrayList();

        User user1 = new User(1, "Alice");
        User user2 = new User(2, "Bob");
        User user3 = new User(3, "Charlie");

        usersList.addUser(user1);
        usersList.addUser(user2);
        usersList.addUser(user3);

        System.out.println("User with ID 2: " + usersList.getUserById(2).getName());
        System.out.println("User at index 1: " + usersList.getUserByIndex(1).getName());
        System.out.println("Total Users: " + usersList.getNumberOfUsers());

        try {
            usersList.getUserById(99);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            usersList.getUserByIndex(10);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
