package com.shecancode.streams;

import java.util.List;

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
}
