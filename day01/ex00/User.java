public class User {

    private int id;
    private String name;
    private int balance;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}