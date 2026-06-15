public class Main {

    public static void main(String[] args) {

        Account acc1 = new Account("ACC001", 5000);
        Account acc2 = new Account("ACC002", 1000);

        BankService service = new BankService();

        try {
            service.transfer(acc1, acc2, 2000);

            System.out.println(
                    "Sender Balance: " + acc1.getBalance());

            System.out.println(
                    "Receiver Balance: " + acc2.getBalance());

            service.transfer(acc1, acc2, 10000);

        } catch (TransactionException e) {
            System.out.println("Transaction Failed:");
            System.out.println(e.getMessage());
        }
    }
}