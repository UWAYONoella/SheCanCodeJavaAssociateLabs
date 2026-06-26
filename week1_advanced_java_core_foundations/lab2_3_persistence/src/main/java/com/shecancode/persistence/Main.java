package com.shecancode.persistence;

public class Main {

    public static void main(String[] args) {

        DatabaseManager.initializeDatabase();

        ProductJdbcRepository repository =
                new ProductJdbcRepository();

        Product laptop =
                new Product(
                        "P001",
                        "Laptop",
                        20,
                        1500.0
                );

        repository.save(laptop);

        Product savedProduct =
                repository.findById("P001");

        if (savedProduct != null) {
            System.out.println("Product found:");
            System.out.println("ID: " + savedProduct.getId());
            System.out.println("Name: " + savedProduct.getName());
            System.out.println("Stock: " + savedProduct.getStock());
            System.out.println("Price: " + savedProduct.getPrice());
        } else {
            System.out.println("Product not found.");
        }
    }
}