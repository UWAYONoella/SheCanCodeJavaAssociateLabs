package com.shecancode.designpatterns;

import java.util.ArrayList;
import java.util.List;

public class OrderEventBus {

    private final List<OrderObserver> observers =
            new ArrayList<>();

    public void subscribe(
            OrderObserver observer) {

        observers.add(observer);
    }

    public void unsubscribe(
            OrderObserver observer) {

        observers.remove(observer);
    }

    public void publish(
            Order order,
            OrderEvent event) {

        for (OrderObserver observer : observers) {

            observer.onEvent(
                    order,
                    event
            );
        }
    }
}