public class ParseError {
    private final int lineNumber;
    private final String line;
    private final String reason;

    public ParseError(int lineNumber, String line, String reason) {
        this.lineNumber = lineNumber;
        this.line = line;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Line " + lineNumber + ": \"" + line + "\" - " + reason;
    }
}