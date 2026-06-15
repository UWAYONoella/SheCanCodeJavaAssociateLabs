public class ExceptionChainingTest {
    public static void main(String[] args) {
        AccountRepository repo = new AccountRepository();

        try {
            repo.readBalance("ACC001", "TXN-1001");
        } catch (DataAccessException e) {
            System.out.println("Caught: " + e.getMessage());

            Throwable cause = e.getCause();
            assertTrue(cause instanceof java.sql.SQLException, "Cause should be SQLException");
            System.out.println("Cause class: " + cause.getClass().getName());
            System.out.println("Cause message: " + cause.getMessage());

            System.out.println("Test passed: getCause() returns original SQLException");
        }
    }

    static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError("FAILED: " + message);
    }
}