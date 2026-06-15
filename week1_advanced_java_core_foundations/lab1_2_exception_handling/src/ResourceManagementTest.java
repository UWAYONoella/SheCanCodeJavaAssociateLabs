public class ResourceManagementTest {
    public static void main(String[] args) throws Exception {
        TransactionFileProcessor processor = new TransactionFileProcessor();
        processor.processFile("transactions.csv", "failed_transactions.txt");

        java.io.File errorFile = new java.io.File("failed_transactions.txt");
        if (errorFile.exists()) {
            System.out.println("\nFailures written to: " + errorFile.getAbsolutePath());
            java.nio.file.Files.lines(errorFile.toPath()).forEach(System.out::println);
        }
    }
}