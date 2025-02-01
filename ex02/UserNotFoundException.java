package ex02;

public class UserNotFoundException extends RuntimeException {
    UserNotFoundException() {
        super("User not found!");
    }

    UserNotFoundException(String cause) {
        super("user not found: " + cause);
    }
}
