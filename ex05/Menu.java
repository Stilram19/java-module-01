package ex05;

import java.util.Scanner;

public class Menu {
    String menu;
    Scanner scanner;
    LaunchMode launchMode;

    enum LaunchMode {
        DEVELOPMENT,
        PRODUCTION;
    }

    enum Command {
        NO_COMMAND,
        ADD_USER,
        VIEW_USER_BALANCE,
        PERFORM_TRANSFER,
        VIEW_USER_TRANSACTIONS,
        REMOVE_TRANSFER,
        CHECK_TRANSFER_VALIDITY,
        FINISH_EXECUTION;
    }

    TransactionsService link;

    Menu(String launchMode, Scanner scanner) {
        this.link = new TransactionsService(new UsersArrayList());
        this.scanner = scanner;

        if (launchMode.equals("dev")) {
            this.launchMode = LaunchMode.DEVELOPMENT;
            this.menu = "1. Add a user\n" +
                        "2. View user balances\n" +
                        "3. Perform a transfer\n" +
                        "4. View all transactions for a specific user\n" +
                        "5. DEV - remove a transfer by ID\n" +
                        "6. DEV - check transfer validity\n" +
                        "7. Finish execution";
            return ;
        }

        this.launchMode = LaunchMode.PRODUCTION;
        this.menu = "1. Add a user\n" +
                        "2. View user balances\n" +
                        "3. Perform a transfer\n" +
                        "4. View all transactions for a specific user\n" +
                        "5. Finish execution";
    }

    public void launch() {
        boolean firstFlag = true;

        while (true) {
            try {
                if (firstFlag == false) {
                    System.out.println("---------------------------------------------------------");
                }
                firstFlag = false;

                Command command = takeUsersCommand();

                if (command == Command.NO_COMMAND) {
                    continue ;
                }
                if (command == Command.FINISH_EXECUTION) {
                    return ;
                }
                if (command == Command.ADD_USER) {
                    this.addUser();
                    continue ;
                }
                if (command == Command.VIEW_USER_BALANCE) {
                    this.displayUserBalance();
                    continue ;
                }
                if (command == Command.PERFORM_TRANSFER) {
                    this.performTransfer();
                    continue ;
                }
                if (command == Command.VIEW_USER_TRANSACTIONS) {
                    this.displayUserTransactions();
                    continue ;
                }
                if (command == Command.REMOVE_TRANSFER) {
                    this.removeTransaction();
                    continue ;
                }
                if (command == Command.CHECK_TRANSFER_VALIDITY) {
                    this.checkTransferValidity();
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                // clear input buffer
                if (this.scanner.hasNextLine()) {
                    this.scanner.nextLine();
                }
            }
        }
    }

    void addUser() {
        System.out.println("Enter a user name and a balance");
        if (this.scanner.hasNextLine() == false) {
            return ;
        }
        String[] userInfos = this.scanner.nextLine().split(" ");

        if (userInfos.length != 2) {
            throw new RuntimeException("user infos must be two tokens separated by space (no spaces in name): <name> <balance>");
        }

        try {
            String userName = userInfos[0];
            int userBalance = Integer.parseInt(userInfos[1]);
            User user = this.link.addUser(userBalance, userName);
            System.out.println("User with id = " + user.getId() + " is added");
        } catch (NumberFormatException e) {
            throw new RuntimeException("balance must be a number (all digits)");
        }
    }

    void displayUserBalance() {
        System.out.println("Enter a user ID");
        if (this.scanner.hasNextInt() == false) {
            return ;
        }
        int userId = this.scanner.nextInt();
        // consume newline if any
        if (this.scanner.hasNextLine()) {
            this.scanner.nextLine();
        }
        System.out.println(this.link.getUserName(userId) + " - " + (int)this.link.getUserBalance(userId));
    }

    void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        if (this.scanner.hasNextInt() == false) {
            return ;
        }
        int senderId = this.scanner.nextInt();

        if (this.scanner.hasNextInt() == false) {
            return ;
        }
        int recipientId = this.scanner.nextInt();

        if (this.scanner.hasNextInt() == false) {
            return ;
        }

        int transferAmount = this.scanner.nextInt();
        
        // consume newline if any
        if (this.scanner.hasNextLine()) {
            this.scanner.nextLine();
        }
        this.link.transfer(senderId, recipientId, transferAmount);
        System.out.println("The transfer is completed");
    }

    void displayUserTransactions() {
        System.out.println("Enter a user ID");
        if (this.scanner.hasNextInt() == false) {
            return ;
        }
        int userId = this.scanner.nextInt();
        Transaction[] transactions = this.link.getUserTransfers(userId);

        // consume newline if any
        if (this.scanner.hasNextLine()) {
            this.scanner.nextLine();
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    void removeTransaction() {
        System.out.println("Enter a user ID and a transfer ID");

        if (this.scanner.hasNextInt() == false) {
            return ;
        }
        int userId = this.scanner.nextInt();

        if (this.scanner.hasNext() == false) {
            return ;
        }
        String uuid = this.scanner.next();

        // consume newline if any
        if (this.scanner.hasNextLine()) {
            this.scanner.nextLine();
        }

        Transaction deletedTransaction = this.link.removeTransaction(userId, uuid);
        User otherUser = deletedTransaction.getRecipient().getId() == userId ? deletedTransaction.getSender() : deletedTransaction.getRecipient();
        if (deletedTransaction.getRecipient().getId() == userId) {
            System.out.print("Transfer From ");
        } else {
            System.out.print("Transfer To ");
        }
        double amount = deletedTransaction.getTransferAmount();
        if (amount < 0) {
            amount = -amount;
        }
        System.out.println(otherUser.getName() + "(id = " + otherUser.getId() + ") " + (int)deletedTransaction.getTransferAmount() + " removed");
    }

    void checkTransferValidity() {
        Transaction[] unpairedTransactions = this.link.checkTransactionsValidity();
        System.out.println("Check results:");

        for (Transaction unpairedTransaction : unpairedTransactions) {
            User sender = unpairedTransaction.getSender();
            User recipient = unpairedTransaction.getRecipient();

            System.out.print(recipient.getName() + "(" + recipient.getId() + ") has an unacknowledged transfer id = ");
            System.out.print(unpairedTransaction.getIdentifier());
            System.out.println("  from " + sender.getName() + "(id = " + sender.getId() + ") for " + (int)unpairedTransaction.getTransferAmount());
        }
    }

    private void displayMenu() {
        System.out.println(this.menu);
    }

    private Command takeUsersCommand() {
        displayMenu();

        if (!this.scanner.hasNextInt()) {
            return Command.FINISH_EXECUTION;
        }
        int command = this.scanner.nextInt();

        // consume newline if any
        if (this.scanner.hasNextLine()) {
            this.scanner.nextLine();
        }

        switch (command) {
            case 1:
                return Command.ADD_USER;
            case 2:
                return Command.VIEW_USER_BALANCE;
            case 3:
                return Command.PERFORM_TRANSFER;
            case 4:
                return Command.VIEW_USER_TRANSACTIONS;
            case 5:
                if (this.launchMode == LaunchMode.DEVELOPMENT) {
                    return Command.REMOVE_TRANSFER;
                }
                return Command.FINISH_EXECUTION;
            case 6:
                if (this.launchMode == LaunchMode.DEVELOPMENT) {
                    return Command.CHECK_TRANSFER_VALIDITY;
                }
                return Command.NO_COMMAND;
            case 7:
                if (this.launchMode == LaunchMode.DEVELOPMENT) {
                    return Command.FINISH_EXECUTION;
                }
                return Command.NO_COMMAND;
            default:
                return Command.NO_COMMAND;
        }
    }
}
