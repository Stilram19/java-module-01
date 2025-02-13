package ex05;

// singleton pattern to have a unique user identifier object in the whole app
class UserIdsGenerator {
    private int currId;
    private static UserIdsGenerator instance;

    private UserIdsGenerator() {}

    public int generateId() {
        return (++this.currId);
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }

        return (instance);
    }
}
