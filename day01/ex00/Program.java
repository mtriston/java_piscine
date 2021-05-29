public class Program {
    public static void main(String[] args) {

        User user1 = new User(1, "Mike", 100);

        User user2 = new User(2, "Tom", 50);
        user2.setBalance(-200);
        user2.setBalance(200);

        System.out.print("User1: ");
        user1.print();
        System.out.print("User2: ");
        user2.print();

        Transaction transaction = new Transaction(user1, user2, 50);
        Transaction transaction1 = new Transaction(user1, user2, -50);

        System.out.print("\nTransaction: ");
        transaction.print();
        System.out.print("\nTransaction1: ");
        transaction1.print();
    }
}