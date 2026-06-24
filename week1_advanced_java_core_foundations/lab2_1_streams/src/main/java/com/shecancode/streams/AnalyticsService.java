package com.shecancode.streams;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Collector;

public class AnalyticsService {

    public List<LineItem> getAllLineItems(List<Order> orders) {

        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .toList();
    }

    public double totalRevenue(List<Order> orders) {

        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .filter(item -> item.getQuantity() > 5)
                .map(item -> item.revenue())
                .reduce(0.0, Double::sum);
    }

    public List<String> topNProductsByRevenue(List<Order> orders, int n) {

        return orders.stream()
                .flatMap(order -> order.getItems().stream())

                .collect(Collectors.groupingBy(
                        item -> item.getProduct().getName(),
                        Collectors.summingDouble(LineItem::revenue)
                ))

                .entrySet()
                .stream()

                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))

                .limit(n)

                .map(Map.Entry::getKey)

                .toList();
    }

    public Map<String, Long> countItemsByCategory(List<Order> orders) {

        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        LineItem::getCategory,
                        Collectors.counting()
                ));
    }

    public Map<Boolean, List<Order>> partitionOrders(List<Order> orders) {

        return orders.stream()
                .collect(Collectors.partitioningBy(
                        Order::isDelivered
                ));
    }

    public Map<String, Double> productAveragePrices(List<Order> orders) {

        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toMap(
                        item -> item.getProduct().getId(),
                        item -> item.getProduct().getPrice(),

                        (existingPrice, newPrice) ->
                                (existingPrice + newPrice) / 2
                ));
    }

    public RevenueReport generateRevenueReport(List<Order> orders) {

        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(
                        Collector.of(
                                RevenueReport::new,
                                RevenueReport::addLineItem,
                                RevenueReport::combine
                        )
                );
    }

    public List<String> topNProductsByRevenueParallel(
            List<Order> orders,
            int n) {

        return orders.parallelStream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        item -> item.getProduct().getName(),
                        Collectors.summingDouble(LineItem::revenue)
                ))
                .entrySet()
                .stream()
                .sorted((a, b) ->
                        Double.compare(b.getValue(), a.getValue()))
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }
}
