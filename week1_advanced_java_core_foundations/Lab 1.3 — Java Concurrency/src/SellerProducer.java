public class SellerProducer implements Runnable {

    private OrderBook orderBook;
    private int number;

    public SellerProducer(OrderBook orderBook,
                          int number) {

        this.orderBook = orderBook;
        this.number = number;
    }

    @Override
    public void run() {

        for (int i = 1; i <= 10; i++) {

            Order order =
                    new Order(
                            "S" + number + "-" + i,
                            "SELL",
                            100
                    );

            orderBook.addOrder(order);
        }
    }
}