package com.shecancode.streams;

public class RevenueReport {

    private double totalRevenue;
    private int itemCount;
    private double maxRevenue;

    public RevenueReport() {
        this.totalRevenue = 0;
        this.itemCount = 0;
        this.maxRevenue = 0;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getItemCount() {
        return itemCount;
    }

    public double getMaxRevenue() {
        return maxRevenue;
    }

    public void addLineItem(LineItem item) {

        double revenue = item.revenue();

        totalRevenue += revenue;
        itemCount++;

        if (revenue > maxRevenue) {
            maxRevenue = revenue;
        }
    }

    public RevenueReport combine(RevenueReport other) {

        this.totalRevenue += other.totalRevenue;
        this.itemCount += other.itemCount;
        this.maxRevenue =
                Math.max(this.maxRevenue, other.maxRevenue);

        return this;
    }
}