package ex02;

// unchecked exception (extends RuntimeException)
public class UserNotFoundException extends RuntimeException {
    UserNotFoundException() {
        super("User not found!");
    }

    UserNotFoundException(String cause) {
        super("user not found: " + cause);
    }
}
