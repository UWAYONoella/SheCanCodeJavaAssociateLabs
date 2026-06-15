public class FraudDetectedException extends TransactionException {

    public FraudDetectedException(String message, String errorCode) {
        super(message, errorCode);
    }
}