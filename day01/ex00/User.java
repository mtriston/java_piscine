public class User {

    private final Integer id;
    private final String name;
    private Integer balance;

    public User(Integer id, String name, Integer balance) {
        this.id = id;
        this.name = name;
        if (balance < 0) {
            this.balance = 0;
            System.err.println("Balance cannot be negative!");
        } else {
            this.balance = balance;
        }
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