public interface UsersList {

    void addUser(User user);
    User getUserById(Integer id) throws RuntimeException;
    User getUserByIndex(Integer index) throws RuntimeException;
    Integer getNumberOfUsers();
}