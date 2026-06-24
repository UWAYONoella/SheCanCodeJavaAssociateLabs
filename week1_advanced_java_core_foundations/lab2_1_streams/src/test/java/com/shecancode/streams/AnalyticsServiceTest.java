package com.shecancode.streams;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyticsServiceTest {

    @Test
    void sequentialAndParallelShouldMatch() {

        Product laptop =
                new Product("P1", "Laptop",
                        "Electronics", 1000);

        LineItem item =
                new LineItem(laptop, 10);

        Order order =
                new Order(
                        "O1",
                        List.of(item),
                        true
                );

        List<Order> orders =
                List.of(order);

        AnalyticsService service =
                new AnalyticsService();

        List<String> sequential =
                service.topNProductsByRevenue(
                        orders,
                        5
                );

        List<String> parallel =
                service.topNProductsByRevenueParallel(
                        orders,
                        5
                );

        assertEquals(sequential, parallel);
    }
}