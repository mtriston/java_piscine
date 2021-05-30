public class UsersArrayList implements UsersList {

    private final Integer START_CAPACITY = 10;

    private Integer listCapacity;
    private Integer listSize;
    private User[] userList;

    public UsersArrayList() {
        this.listCapacity = 10;
        this.listSize = 0;
        this.userList = new User[this.listCapacity];
    }

    private void expandList() {
        listCapacity *= 2;
        User[] tmp = new User[listCapacity];
        for (int i = 0; i < userList.length; ++i) {
            tmp[i] = userList[i];
        }
        userList = tmp;
    }

    @Override
    public void addUser(User user) {
        if (listSize.equals(listCapacity)) {
            expandList();
        }
        userList[listSize++] = user;
    }

    @Override
    public User getUserById(Integer id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUserByIndex(Integer index) {
        if (index >= listSize) {
            throw new UserNotFoundException();
        } else {
            return userList[index];
        }
    }

    @Override
    public Integer getNumberOfUsers() {
        return listSize;
    }
}
