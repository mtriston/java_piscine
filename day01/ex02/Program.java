public class Program {

    public static void main(String[] args) {

        UsersArrayList usersArrayList = new UsersArrayList();

        for (int i = 0; i < 20; ++i) {
            usersArrayList.addUser(new User("User" + i, 100 * i));
        }

        for (int i = 0; i < 20; ++i) {
            usersArrayList.getUserByIndex(i).print();
            usersArrayList.getUserByIndex(i).print();
        }

        try {
            usersArrayList.getUserByIndex(22);
        } catch (UserNotFoundException e) {
            System.out.println("User not found");
        }

        try {
            usersArrayList.getUserById(22);
        } catch (UserNotFoundException e) {
            System.out.println("User not found");
        }

        System.out.printf("Number of users = %d\n", usersArrayList.getNumberOfUsers());
    }
}
