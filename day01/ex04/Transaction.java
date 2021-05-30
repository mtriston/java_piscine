import java.util.UUID;

enum TransferCategory { DEBIT, CREDIT }

public class Transaction {

    private final UUID id;
    private final User recipient;
    private final User sender;
    private final TransferCategory transferCategory;
    private final int transferAmount;


    public Transaction(UUID id, User recipient, User sender, TransferCategory transferCategory, int transferAmount) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void print() {
        System.out.printf("id %s\n", id);
        System.out.print("Recipient: ");
        recipient.print();
        System.out.print("Sender: ");
        sender.print();
        System.out.printf("Transfer category: %s\n", transferCategory);
        System.out.printf("Transfer amount: %d\n", transferAmount);
    }
}