package com.shecancode.streams;

public class LineItem {

    private final Product product;
    private final int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return product.getCategory();
    }

    public double revenue() {
        return product.getPrice() * quantity;
    }
}