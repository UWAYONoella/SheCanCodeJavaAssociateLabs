package com.shecancode.designpatterns;

public class MobileMoneyStrategy
        implements PaymentStrategy {

    @Override
    public PaymentResult process(
            PaymentRequest request) {

        double fee =
                request.getAmount() * 0.02;

        return new PaymentResult(
                request.getAmount(),
                fee,
                request.getAmount() + fee
        );
    }
}