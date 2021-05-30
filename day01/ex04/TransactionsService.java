import java.util.UUID;

public class TransactionsService {

    private final UsersList usersList = new UsersArrayList();

    public void addUser(String name, Integer balance) {
        usersList.addUser(new User(name, balance));
    }

    public Integer getUserBalance(Integer id) throws UserNotFoundException {
        return usersList.getUserById(id).getBalance();
    }

    public void performTransaction(Integer senderId, Integer recipientId, Integer amount) throws IllegalTransactionException, UserNotFoundException {

        User sender = usersList.getUserById(senderId);
        User recipient = usersList.getUserById(recipientId);

        if (sender.getBalance() < amount || senderId.equals(recipientId)) {
            throw new IllegalTransactionException();
        } else {
            UUID id = UUID.randomUUID();
            sender.transactionsList.addTransaction(new Transaction(id, recipient, sender, TransferCategory.CREDIT, amount));
            recipient.transactionsList.addTransaction(new Transaction(id, recipient, sender, TransferCategory.DEBIT, amount));
            sender.setBalance(sender.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
        }
    }

    public Transaction[] getUserTransaction(Integer id) throws UserNotFoundException {
        return usersList.getUserById(id).transactionsList.transformIntoArray();
    }

    public void removeTransactionById(Integer userId, UUID transactionId) throws UserNotFoundException, TransactionNotFoundException {
        usersList.getUserById(userId).transactionsList.removeTransactionById(transactionId);
    }

    public Transaction[] checkValidityOfTransactions() {
        TransactionsList unpairedTransactions = new TransactionsLinkedList();
        for (Integer i = 0; i < usersList.getNumberOfUsers(); ++i) {
            User user = usersList.getUserByIndex(i);
            Transaction[] userTransaction = user.transactionsList.transformIntoArray();
            for (Transaction transaction : userTransaction) {
                try {
                    transaction.getRecipient().transactionsList.getTransactionById(transaction.getId());
                    transaction.getSender().transactionsList.getTransactionById(transaction.getId());
                } catch (TransactionNotFoundException e) {
                    unpairedTransactions.addTransaction(transaction);
                }
            }
        }
        return unpairedTransactions.transformIntoArray();
    }
}
