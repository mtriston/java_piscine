public final class UserIdsGenerator {

    private static UserIdsGenerator instance;
    private Integer id;

    private UserIdsGenerator(Integer id) {
        this.id = id;
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator(0);
        }
        return instance;
    }

    public int generateId() {
        return ++id;
    }
}
