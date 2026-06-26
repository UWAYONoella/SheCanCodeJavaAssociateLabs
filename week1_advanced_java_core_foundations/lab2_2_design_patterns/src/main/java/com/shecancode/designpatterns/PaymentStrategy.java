package com.shecancode.designpatterns;

public interface PaymentStrategy {

    PaymentResult process(
            PaymentRequest request
    );
}