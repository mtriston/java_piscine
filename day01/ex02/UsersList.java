public interface UsersList {

    public void addUser(User user);
    public User getUserById(Integer id) throws RuntimeException;
    public User getUserByIndex(Integer index) throws RuntimeException;
    public Integer getNumberOfUsers();
}