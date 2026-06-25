package com.shecancode.designpatterns;

public class PaymentProcessor {

    private PaymentStrategy strategy;

    public PaymentProcessor(
            PaymentStrategy strategy) {

        this.strategy = strategy;
    }

    public void setStrategy(
            PaymentStrategy strategy) {

        this.strategy = strategy;
    }

    public PaymentResult processPayment(
            PaymentRequest request) {

        return strategy.process(request);
    }
}