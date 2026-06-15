import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class AccountRepository {

    private static final Logger logger = LoggerFactory.getLogger(AccountRepository.class);

    public double readBalance(String accountNumber, String transactionId) throws DataAccessException {
        MDC.put("transactionId", transactionId);
        try {
            // Simulate a low-level DB failure
            throw new SQLException("Connection timeout while querying account " + accountNumber);

        } catch (SQLException e) {
            DataAccessException dae = new DataAccessException(
                    "Failed to read balance for account " + accountNumber,
                    "DB-001",
                    e);

            logCauseChain(dae);
            return rethrow(dae);

        } finally {
            MDC.remove("transactionId");
        }
    }

    private double rethrow(DataAccessException dae) throws DataAccessException {
        throw dae;
    }

    private void logCauseChain(Throwable t) {
        logger.error("Exception occurred: {}", t.getMessage());
        Throwable cause = t.getCause();
        while (cause != null) {
            logger.error("Caused by: {} - {}", cause.getClass().getSimpleName(), cause.getMessage());
            cause = cause.getCause();
        }
    }
}