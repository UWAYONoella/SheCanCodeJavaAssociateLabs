package com.shecancode.designpatterns;

public class CreditCardStrategy
        implements PaymentStrategy {

    @Override
    public PaymentResult process(
            PaymentRequest request) {

        double fee =
                request.getAmount() * 0.03;

        return new PaymentResult(
                request.getAmount(),
                fee,
                request.getAmount() + fee
        );
    }
}