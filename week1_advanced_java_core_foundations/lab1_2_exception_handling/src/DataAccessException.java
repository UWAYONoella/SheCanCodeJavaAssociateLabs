public class DataAccessException extends TransactionException {

    public DataAccessException(String message, String errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}