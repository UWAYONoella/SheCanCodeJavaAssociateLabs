package com.shecancode.designpatterns;

public class EmailNotifier
        implements OrderObserver {

    @Override
    public void onEvent(
            Order order,
            OrderEvent event) {

        System.out.println(
                "Email sent for order "
                        + order.getId()
                        + " : "
                        + event
        );
    }
}