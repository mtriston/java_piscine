import java.util.UUID;

public interface TransactionsList {

    void addTransaction(Transaction transaction);

    Transaction getTransactionById(UUID id) throws TransactionNotFoundException;

    Transaction removeTransactionById(UUID id) throws TransactionNotFoundException;
    Transaction[] transformIntoArray();
}