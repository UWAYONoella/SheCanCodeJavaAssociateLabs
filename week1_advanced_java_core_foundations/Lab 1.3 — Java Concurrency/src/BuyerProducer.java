public class BuyerProducer implements Runnable {

    private OrderBook orderBook;
    private int number;

    public BuyerProducer(OrderBook orderBook,
                         int number) {

        this.orderBook = orderBook;
        this.number = number;
    }

    @Override
    public void run() {

        for (int i = 1; i <= 10; i++) {

            Order order =
                    new Order(
                            "B" + number + "-" + i,
                            "BUY",
                            100
                    );

            orderBook.addOrder(order);
        }
    }
}