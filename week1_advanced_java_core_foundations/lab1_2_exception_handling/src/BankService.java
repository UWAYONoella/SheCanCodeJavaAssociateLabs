public class BankService {

    public void transfer(Account from, Account to, double amount)
            throws TransactionException {

        if (amount > 10000) {
            throw new FraudDetectedException(
                    "Suspicious transaction detected",
                    "FRAUD-001");
        }

        if (from.getBalance() < amount) {
            throw new InsufficientFundsException(
                    "Insufficient funds",
                    "FUND-001");
        }

        from.withdraw(amount);
        to.deposit(amount);

        System.out.println("Transfer successful.");
    }
}