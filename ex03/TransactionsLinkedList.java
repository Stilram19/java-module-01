package ex03;

public class TransactionsLinkedList implements TransactionsList {
    private TransactionNode head;
    private TransactionNode tail;
    private int             size;

    public void addTransaction(Transaction newTransaction) {
        TransactionNode newNode = new TransactionNode(newTransaction, null);
    
        if (this.head == null) {
            this.head = newNode;
        }
    
        if (this.tail != null) { 
            this.tail.next = newNode;
        }

        this.tail = newNode;
        this.size++;
    }
    

    public void removeTransaction(String uuid) {
        if (this.size == 0) {
            throw new TransactionNotFoundException("No transaction with such a UUID: '" + uuid + "'");
        }

        TransactionNode prevNode = null;
        TransactionNode currNode = this.head;

        while (currNode != null) {
            if (currNode.transaction.getIdentifier().equals(uuid)) {
                if (prevNode == null) {
                    this.head = currNode.next;
                    if (this.head == null) {
                        this.tail = null;
                    }
                } else {
                    prevNode.next = currNode.next;
                    if (currNode == tail) {
                        this.tail = prevNode;
                    }
                }
                this.size--;
                return;
            }
            prevNode = currNode;
            currNode = currNode.next;
        }

        throw new TransactionNotFoundException("No transaction with such a UUID: '" + uuid + "'");
    }

    public Transaction[] toArray() {
        if (this.size == 0) {
            return new Transaction[0];
        }

        Transaction[] arr = new Transaction[this.size];
        TransactionNode currNode = this.head;

        for (int i = 0; i < this.size; i++) {
            arr[i] = currNode.transaction;
            currNode = currNode.next;
        }

        return arr;
    }
}
