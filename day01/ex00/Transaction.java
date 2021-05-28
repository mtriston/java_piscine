import java.util.UUID;

enum Category { DEBIT, CREDIT }

public class Transaction {

    private UUID id;
    private User recipient;
    private User sender;
    private Category transferCategory;
    private int transferAmount;


}