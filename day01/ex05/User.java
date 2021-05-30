public class User {

    private final Integer id;
    private final String name;
    private Integer balance;
    public TransactionsList transactionsList;

    public User(String name, Integer balance) throws IllegalUserBalanceException {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (balance < 0) {
           throw new IllegalUserBalanceException();
        } else {
            this.balance = balance;
        }
        transactionsList = new TransactionsLinkedList();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.err.println("Balance cannot be negative!");
        }
    }

    public void print() {
        System.out.printf("id %d, name %s, balance %d\n", id, name, balance);
    }
}