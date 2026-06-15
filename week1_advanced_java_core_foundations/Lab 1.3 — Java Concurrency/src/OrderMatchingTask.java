import java.util.concurrent.Callable;

public class OrderMatchingTask
        implements Callable<MatchResult> {

    private OrderBook orderBook;

    public OrderMatchingTask(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    @Override
    public MatchResult call() {

        return orderBook.matchOrdersWithResult();
    }
}