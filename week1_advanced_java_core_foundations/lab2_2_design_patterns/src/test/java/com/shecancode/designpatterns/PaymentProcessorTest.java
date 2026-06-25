package com.shecancode.designpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentProcessorTest {

    @Test
    void shouldSwitchStrategiesAtRuntime() {

        PaymentRequest request =
                new PaymentRequest(1000);

        PaymentProcessor processor =
                new PaymentProcessor(
                        new CreditCardStrategy()
                );

        PaymentResult creditCardResult =
                processor.processPayment(request);

        assertEquals(
                30.0,
                creditCardResult.getFee()
        );

        processor.setStrategy(
                new BankTransferStrategy()
        );

        PaymentResult bankResult =
                processor.processPayment(request);

        assertEquals(
                10.0,
                bankResult.getFee()
        );

        processor.setStrategy(
                new MobileMoneyStrategy()
        );

        PaymentResult mobileMoneyResult =
                processor.processPayment(request);

        assertEquals(
                20.0,
                mobileMoneyResult.getFee()
        );
    }
}