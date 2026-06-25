package com.shecancode.designpatterns;

public class PaymentResult {

    private final double originalAmount;
    private final double fee;
    private final double totalAmount;

    public PaymentResult(
            double originalAmount,
            double fee,
            double totalAmount) {

        this.originalAmount = originalAmount;
        this.fee = fee;
        this.totalAmount = totalAmount;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public double getFee() {
        return fee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}