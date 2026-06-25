package com.shecancode.designpatterns;

public class BankTransferStrategy
        implements PaymentStrategy {

    @Override
    public PaymentResult process(
            PaymentRequest request) {

        double fee =
                request.getAmount() * 0.01;

        return new PaymentResult(
                request.getAmount(),
                fee,
                request.getAmount() + fee
        );
    }
}