import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    static class Node {
        public Transaction transaction;
        public Node next;

        public Node(Transaction transaction, Node next) {
            this.transaction = transaction;
            this.next = next;
        }
    }

    private Node rootNode;
    private Transaction[] transactionArray;

    public TransactionsLinkedList() {
        rootNode = null;
        transactionArray = null;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        rootNode = new Node(transaction, rootNode);
    }

    @Override
    public Transaction getTransactionById(UUID id) {
        transformIntoArray();
        for (Transaction transaction : transactionArray) {
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }
        throw new TransactionNotFoundException();
    }


    @Override
    public Transaction removeTransactionById(UUID id) {
        Node node = rootNode;
        Node prevNode = rootNode;
        while (node != null) {
            if (node.transaction.getId().equals(id)) {
                Transaction transaction = node.transaction;
                if (node != rootNode) {
                    prevNode.next = node.next;
                } else {
                    rootNode = rootNode.next;
                }
                return transaction;
            }
            prevNode = node;
            node = node.next;
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] transformIntoArray() {
        Node node = rootNode;
        Integer i = 0;
        transactionArray = new Transaction[size()];
        while (node != null) {
            transactionArray[i++] = node.transaction;
            node = node.next;
        }
        return transactionArray;
    }

    public void print() {
        transformIntoArray();
        System.out.println("\nTransaction's list\n");
        for (Transaction transaction : transactionArray) {
            transaction.print();
        }
    }

    private Integer size() {
        Node tmp = rootNode;
        Integer i = 0;
        while (tmp != null) {
            ++i;
            tmp = tmp.next;
        }
        return i;
    }
}
