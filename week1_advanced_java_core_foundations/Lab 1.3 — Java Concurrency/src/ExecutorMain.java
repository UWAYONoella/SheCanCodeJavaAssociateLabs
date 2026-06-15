import java.util.concurrent.*;

public class ExecutorMain {

    public static void main(String[] args) {

        double totalThroughput = 0;

        for (int run = 1; run <= 5; run++) {

            OrderBook orderBook =
                    new OrderBook();

            // Generate orders
            for (int i = 1; i <= 1000; i++) {

                orderBook.addOrder(
                        new Order(
                                "B" + i,
                                "BUY",
                                100
                        )
                );

                orderBook.addOrder(
                        new Order(
                                "S" + i,
                                "SELL",
                                100
                        )
                );
            }

            ExecutorService executor =
                    Executors.newFixedThreadPool(
                            Runtime.getRuntime()
                                    .availableProcessors()
                    );

            long startTime =
                    System.currentTimeMillis();

            Future<MatchResult> future =
                    executor.submit(
                            new OrderMatchingTask(
                                    orderBook
                            )
                    );

            try {

                MatchResult result =
                        future.get();

                long endTime =
                        System.currentTimeMillis();

                double seconds =
                        (endTime - startTime)
                                / 1000.0;

                double throughput =
                        result.getMatchedOrders()
                                / seconds;

                totalThroughput += throughput;

                System.out.println(
                        "Run "
                                + run
                                + " -> "
                                + result
                );

                System.out.println(
                        "Throughput: "
                                + throughput
                                + " matches/sec"
                );

            } catch (InterruptedException e) {

                System.out.println(
                        "Thread interrupted"
                );

            } catch (ExecutionException e) {

                System.out.println(
                        "Execution failed: "
                                + e.getMessage()
                );
            }

            executor.shutdown();
        }

        System.out.println(
                "\nAverage Throughput: "
                        + (totalThroughput / 5)
                        + " matches/sec"
        );
    }
}