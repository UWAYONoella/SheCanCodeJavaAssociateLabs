package com.shecancode.designpatterns;

public class AuditLogger
        implements OrderObserver {

    @Override
    public void onEvent(
            Order order,
            OrderEvent event) {

        System.out.println(
                "Audit log created for order "
                        + order.getId()
                        + " : "
                        + event
        );
    }
}