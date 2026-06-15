import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileProcessor {

    public void processFile(String inputPath, String errorOutputPath) throws IOException {
        List<ParseError> errors = new ArrayList<>();
        int processed = 0;
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;

                String[] fields = line.split(",");
                if (fields.length != 4) {
                    errors.add(new ParseError(lineNumber, line, "Expected 4 fields, found " + fields.length));
                    continue;
                }

                try {
                    String txnId = fields[0];
                    String fromAcc = fields[1];
                    String toAcc = fields[2];
                    double amount = Double.parseDouble(fields[3]);

                    // "Process" the transaction (placeholder)
                    System.out.println("Processed " + txnId + ": " + fromAcc + " -> " + toAcc + " = " + amount);
                    processed++;

                } catch (NumberFormatException e) {
                    errors.add(new ParseError(lineNumber, line, "Invalid amount format"));
                }
            }
        }

        System.out.println("Processed: " + processed + ", Failed: " + errors.size());

        if (!errors.isEmpty()) {
            try (FileWriter writer = new FileWriter(errorOutputPath)) {
                for (ParseError error : errors) {
                    writer.write(error.toString());
                    writer.write(System.lineSeparator());
                }
            }
        }
    }
}