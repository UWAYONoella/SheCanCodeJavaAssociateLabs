public class Main {

    public static void main(String[] args)
            throws InterruptedException {

        OrderBook orderBook =
                new OrderBook();

        Thread[] buyers =
                new Thread[10];

        Thread[] sellers =
                new Thread[10];

        // Create 10 buyers
        for (int i = 0; i < 10; i++) {

            buyers[i] =
                    new Thread(
                            new BuyerProducer(
                                    orderBook,
                                    i + 1
                            )
                    );

            buyers[i].start();
        }

        // Create 10 sellers
        for (int i = 0; i < 10; i++) {

            sellers[i] =
                    new Thread(
                            new SellerProducer(
                                    orderBook,
                                    i + 1
                            )
                    );

            sellers[i].start();
        }

        // Wait for buyers
        for (Thread buyer : buyers) {
            buyer.join();
        }

        // Wait for sellers
        for (Thread seller : sellers) {
            seller.join();
        }

        System.out.println(
                "\nBuy Orders: "
                        + orderBook.getBuyCount()
        );

        System.out.println(
                "Sell Orders: "
                        + orderBook.getSellCount()
        );

        orderBook.matchOrders();

        System.out.println(
                "\nAfter Matching"
        );

        System.out.println(
                "Buy Orders: "
                        + orderBook.getBuyCount()
        );

        System.out.println(
                "Sell Orders: "
                        + orderBook.getSellCount()
        );
    }
}