package ex05;

import java.util.Arrays;

class UsersArrayList implements UsersList {
    private int     size; // the number of users (not the size of the array which is the capacity)
    private User[]  users;

    UsersArrayList() {
        this.size = 0;
        this.users = new User[10];
    }

    public void addUser(User newUser) {
        if (size == users.length) {
            this.users = Arrays.copyOf(this.users, this.users.length * 2);
        }

        this.users[size++] = newUser;
    }

    public User getUserById(int id) {
        for (int i = 0; i < this.size; i++) {
            if (id == this.users[i].getId()) {
                return (users[i]);
            }
        }

        throw new UserNotFoundException("no user with such id: \'" + id + '\'');
    }

    public User getUserByIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new UserNotFoundException("no user with such index: \'" + index + '\'');
        }
        return (users[index]);
    }

    public int  getNumberOfUsers() {
        return (this.size);
    }
}
