import java.util.Scanner;

enum ProfileType { PRODUCTION, DEV }

public class Menu {

    interface Operation {
        void perform();
    }

    private final TransactionsService transactionsService;
    private final ProfileType profileType;
    private final Scanner scanner;
    private final Operation[] operations;

    public Menu(ProfileType profileType) {
        this.transactionsService = new TransactionsService();
        this.profileType = profileType;
        this.scanner = new Scanner(System.in);
        if (profileType == ProfileType.DEV) {
            this.operations = new Operation[] {
                    this::printMenu,
                    this::addUser,
                    this::viewUserBalances,
                    this::performTransfer,
                    this::viewAllUserTransactions,
                    this::removeTransferById,
                    this::checkTransferValidity,
                    this::finish,
            };
        } else {
            this.operations = new Operation[] {
                    this::printMenu,
                    this::addUser,
                    this::viewUserBalances,
                    this::performTransfer,
                    this::viewAllUserTransactions,
                    this::finish,
            };
        }
    }

    private void printMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (profileType == ProfileType.DEV) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check transfer validity");
            System.out.println("7. Finish execution");
        } else {
            System.out.println("5. Finish execution");
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        try {
            User user = new User(scanner.next(), scanner.nextInt());
            transactionsService.addUser(user);
            System.out.printf("User with id = %d is added\n", user.getId());
        } catch (IllegalUserBalanceException e) {
            System.err.println("Illegal user's balance");
        } catch (RuntimeException e) {
            System.err.println("Invalid input!");
        }
        scanner.nextLine();
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        try {
            Integer senderId = scanner.nextInt();
            Integer recipientId = scanner.nextInt();
            Integer amount = scanner.nextInt();
            transactionsService.performTransaction(senderId, recipientId, amount);
            System.out.println("The transfer is completed");
        } catch (UserNotFoundException e) {
            System.err.println("User not found");
        } catch (IllegalTransactionException e) {
            System.err.println("Illegal transaction");
        } catch (RuntimeException e) {
            System.err.println("Invalid input!");
        }
        scanner.nextLine();
    }

    private void viewUserBalances() {
        System.out.println("Enter a user ID");
        try {
            Integer id = scanner.nextInt();
            System.out.printf("%s - %d\n", transactionsService.getUserName(id), transactionsService.getUserBalance(id));
        } catch (UserNotFoundException e) {
            System.err.println("User not found");
        } catch (RuntimeException e) {
            System.err.println("Invalid input!");
        }
        scanner.nextLine();
    }

    private void viewAllUserTransactions() {
        System.out.println("Enter a user ID");
        try {
            Transaction[] transactions = transactionsService.getUserTransaction(scanner.nextInt());
            for (Transaction transaction: transactions) {
                if (transaction.getTransferCategory() == TransferCategory.DEBIT) {
                    System.out.printf("From %s(id = %d) +%d with id = %s\n",
                            transaction.getSender().getName(),
                            transaction.getSender().getId(),
                            transaction.getTransferAmount(),
                            transaction.getId()
                    );
                } else {
                    System.out.printf("To %s(id = %d) -%d with id = %s\n",
                            transaction.getRecipient().getName(),
                            transaction.getRecipient().getId(),
                            transaction.getTransferAmount(),
                            transaction.getId()
                    );
                }
            }
        } catch (UserNotFoundException e) {
            System.err.println("User not found");
        } catch (RuntimeException e) {
        System.err.println("Invalid input!");
        }
        scanner.nextLine();
    }

    private void removeTransferById() {
        System.out.println("Enter a user ID and a transfer ID");
        try {
            Transaction transaction = transactionsService.removeTransactionById(scanner.nextInt(), scanner.next());
            if (transaction.getTransferCategory() == TransferCategory.DEBIT) {
                System.out.printf("Transfer from %s(id = %d) %d removed\n",
                        transaction.getSender().getName(),
                        transaction.getSender().getId(),
                        transaction.getTransferAmount()
                );
            } else {
                System.out.printf("Transfer to %s(id = %d) %d removed\n",
                        transaction.getRecipient().getName(),
                        transaction.getRecipient().getId(),
                        transaction.getTransferAmount()
                );
            }
        } catch (UserNotFoundException e) {
            System.err.println("User not found");
        } catch (TransactionNotFoundException e) {
            System.err.println("Transaction not found");
        } catch (RuntimeException e) {
            System.err.println("Invalid input!");
        }
        scanner.nextLine();
    }

    private void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] transactions = transactionsService.checkValidityOfTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getTransferCategory() == TransferCategory.DEBIT) {
                System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s to %s(id = %d) for %d\n",
                        transaction.getSender().getName(),
                        transaction.getSender().getId(),
                        transaction.getId(),
                        transaction.getRecipient().getName(),
                        transaction.getRecipient().getId(),
                        transaction.getTransferAmount()
                );
            } else {
                System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s from %s(id = %d) for %d\n",
                        transaction.getRecipient().getName(),
                        transaction.getRecipient().getId(),
                        transaction.getId(),
                        transaction.getSender().getName(),
                        transaction.getSender().getId(),
                        transaction.getTransferAmount()
                );
            }
        }
    }

    public void start() {
        while (true) {
            operations[0].perform();
            try {
                Integer command = scanner.nextInt();
                operations[command].perform();
            } catch (RuntimeException e) {
                break;
            }
            System.out.println("---------------------------------------------------------");
        }
    }

    public void finish() {
        scanner.close();
        System.exit(0);
    }
}