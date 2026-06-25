package com.shecancode.designpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class OrderEventBusTest {

    @Test
    void shouldNotifyAllObservers() {

        OrderEventBus bus =
                new OrderEventBus();

        bus.subscribe(
                new EmailNotifier()
        );

        bus.subscribe(
                new InventoryUpdater()
        );

        bus.subscribe(
                new AuditLogger()
        );

        Order order =
                new Order("ORD-001");

        assertDoesNotThrow(
                () -> bus.publish(
                        order,
                        OrderEvent.ORDER_DELIVERED
                )
        );
    }
}