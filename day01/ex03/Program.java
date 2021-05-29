public class Program {
    public static void main(String[] args) {
        User user = new User("Mike", 200);
        User user1 = new User("Nike", 100);
        TransactionsLinkedList list = new TransactionsLinkedList();
        TransactionsList transactionsList = list;
        transactionsList.addTransaction(new Transaction(user, user1, 1));
        transactionsList.addTransaction(new Transaction(user, user1, 2));
        transactionsList.addTransaction(new Transaction(user, user1, 3));
        transactionsList.addTransaction(new Transaction(user, user1, 4));
        transactionsList.addTransaction(new Transaction(user, user1, 5));
        transactionsList.addTransaction(new Transaction(user, user1, 6));
        transactionsList.addTransaction(new Transaction(user, user1, 7));
        transactionsList.addTransaction(new Transaction(user, user1, 8));
        transactionsList.addTransaction(new Transaction(user, user1, 9));
        transactionsList.addTransaction(new Transaction(user, user1, 10));
        list.print();
    }
}
