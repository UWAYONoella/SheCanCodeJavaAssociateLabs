package com.shecancode.streams;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkRunner {

    public static void main(String[] args) {

        Product product =
                new Product(
                        "P1",
                        "Laptop",
                        "Electronics",
                        1000
                );

        List<Order> orders =
                new ArrayList<>();

        for (int i = 0; i < 100000; i++) {

            LineItem item =
                    new LineItem(product, 10);

            orders.add(
                    new Order(
                            "O" + i,
                            List.of(item),
                            true
                    )
            );
        }

        AnalyticsService service =
                new AnalyticsService();

        long start1 = System.currentTimeMillis();

        service.topNProductsByRevenue(
                orders,
                5
        );

        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();

        service.topNProductsByRevenueParallel(
                orders,
                5
        );

        long end2 = System.currentTimeMillis();

        System.out.println(
                "Sequential: "
                        + (end1 - start1)
                        + " ms"
        );

        System.out.println(
                "Parallel: "
                        + (end2 - start2)
                        + " ms"
        );
    }
}