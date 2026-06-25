package com.shecancode.designpatterns;

public interface OrderObserver {

    void onEvent(
            Order order,
            OrderEvent event
    );
}