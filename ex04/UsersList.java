package ex04;

interface UsersList {
    void addUser(User newUser); // methods public by default
    User getUserById(int id);
    User getUserByIndex(int index);
    int  getNumberOfUsers();
}
