public class InsufficientFundsException extends TransactionException {

    public InsufficientFundsException(String message, String errorCode) {
        super(message, errorCode);
    }
}