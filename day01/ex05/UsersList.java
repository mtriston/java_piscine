public interface UsersList {

    void addUser(User user) throws IllegalUserBalanceException;
    User getUserById(Integer id) throws UserNotFoundException;
    User getUserByIndex(Integer index) throws UserNotFoundException;
    Integer getNumberOfUsers();
}