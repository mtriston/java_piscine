public class Program {
    public static void main(String[] args) {
        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser("Alex", 100);
        transactionsService.addUser("Mike", 50);
        transactionsService.addUser("Nik", 200);
        transactionsService.addUser("Alice", 1000);
        transactionsService.addUser("Ann", 10);
        transactionsService.addUser("Tom", 500);

        try {
            transactionsService.performTransaction(1, 2, 50);
        } catch (UserNotFoundException ignored) {
            System.err.println("User not found");
        } catch (IllegalTransactionException ignored) {
            System.err.println("Illegal transaction");
        }

        try {
            transactionsService.performTransaction(1, 2, 500);
        } catch (UserNotFoundException ignored) {
            System.err.println("User not found");
        } catch (IllegalTransactionException ignored) {
            System.err.println("Illegal transaction");
        }

        try {
            transactionsService.performTransaction(1, 1, 1);
        } catch (IllegalTransactionException ignored) {
            System.err.println("Illegal transaction");
        }

        transactionsService.performTransaction(4, 1, 10);
        transactionsService.performTransaction(4, 2, 10);
        transactionsService.performTransaction(4, 3, 10);
        transactionsService.performTransaction(4, 5, 10);
        transactionsService.performTransaction(4, 6, 10);

        System.out.println("\nPrint all transactions of 4th user");
        Transaction []transactions = transactionsService.getUserTransaction(4);
        for (Transaction transaction : transactions ) {
            transaction.print();
            transactionsService.removeTransactionById(4, transaction.getId());
        }

        System.out.println("\nPrint all unpaired transactions");
        Transaction []unpairedTransactions = transactionsService.checkValidityOfTransactions();
        for (Transaction transaction : unpairedTransactions ) {
            transaction.print();
        }
    }
}
