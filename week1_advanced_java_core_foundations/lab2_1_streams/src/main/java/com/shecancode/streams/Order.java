package com.shecancode.streams;

import java.util.List;

public class Order {

    private final String id;
    private final List<LineItem> items;
    private final boolean delivered;

    public Order(String id, List<LineItem> items, boolean delivered) {
        this.id = id;
        this.items = items;
        this.delivered = delivered;
    }

    public String getId() {
        return id;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public boolean isDelivered() {
        return delivered;
    }
}