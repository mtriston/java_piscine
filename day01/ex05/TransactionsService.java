import java.util.UUID;

public class TransactionsService {

    private final UsersList usersList = new UsersArrayList();

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public Integer getUserBalance(Integer id) throws UserNotFoundException {
        return usersList.getUserById(id).getBalance();
    }

    public String getUserName(Integer id) throws UserNotFoundException {
        return usersList.getUserById(id).getName();
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

    public Transaction removeTransactionById(Integer userId, String transactionId) throws UserNotFoundException, TransactionNotFoundException {
        return usersList.getUserById(userId).transactionsList.removeTransactionById(UUID.fromString(transactionId));
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
