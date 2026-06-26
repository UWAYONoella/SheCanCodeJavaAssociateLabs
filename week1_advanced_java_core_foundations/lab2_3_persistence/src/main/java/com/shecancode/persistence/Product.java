package com.shecancode.persistence;

public class Product {

    private final String id;
    private final String name;
    private int stock;
    private final double price;

    public Product(
            String id,
            String name,
            int stock,
            double price) {

        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}