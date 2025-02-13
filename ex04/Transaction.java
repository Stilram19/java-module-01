package ex04;
import java.util.UUID;

enum TransactionType {
    DEBIT,
    CREDIT;
}

class Transaction {
    private final String          identifier;
    private final User            recipient;
    private final User            sender;
    private final double          transferAmount;
    private final TransactionType transferCategory;

    public Transaction(TransactionType transferCategory, double transferAmount, User sender, User recipient, String uuid) {
        if (sender == null || recipient == null) {
            throw new IllegalArgumentException("Sender and recipient cannot be null.");
        }

        // comparing with == since enums are singletons
        if (transferCategory == TransactionType.CREDIT && transferAmount >= 0) {
            throw new IllegalArgumentException("transferAmount must be a negative value when Transaction Type is CREDIT.");
        }

        if (transferCategory == TransactionType.DEBIT && transferAmount <= 0) {
            throw new IllegalArgumentException("transferAmount must be a positive value when Transaction Type is DEBIT.");
        }

        this.transferCategory = transferCategory;
        this.transferAmount = transferAmount;
        this.recipient = recipient; // reference assignment (no deep copies)
        this.sender = sender;
        this.identifier = uuid == null ? UUID.randomUUID().toString() : uuid;

        if (transferAmount < 0) {
            transferAmount = -transferAmount;
        }

        // update balance of recipient
        if (transferCategory == TransactionType.DEBIT) {
            this.recipient.deposit(transferAmount);
            return ;
        }
        this.sender.withdraw(transferAmount);
    }

    public String getIdentifier() {
        return (this.identifier);
    }

    public double getTransferAmount() {
        return (this.transferAmount);
    }

    public TransactionType getTransferCategory() {
        return (this.transferCategory);
    }

    public User getRecipient() {
        return (this.recipient);
    }

    public User getSender() {
        return (this.sender);
    }

    @Override
    public String toString() {
        String direction = transferCategory == TransactionType.CREDIT ? 
        "To " + recipient.getName() + "(id = " + recipient.getId() + ")" :
            "From " + sender.getName() + "(id = " + sender.getId() + ")";

        return String.format("%s %.2f with id = %s", direction, transferAmount, identifier);
    }
}
