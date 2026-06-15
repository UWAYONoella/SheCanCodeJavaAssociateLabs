public class MatchResult {

    private int matchedOrders;

    public MatchResult(int matchedOrders) {
        this.matchedOrders = matchedOrders;
    }

    public int getMatchedOrders() {
        return matchedOrders;
    }

    @Override
    public String toString() {
        return "Matched Orders: " + matchedOrders;
    }
}