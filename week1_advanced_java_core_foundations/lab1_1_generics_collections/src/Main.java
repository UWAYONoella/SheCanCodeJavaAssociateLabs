public class Main {
    public static void main(String[] args) {

        WarehouseStore<Product> store = new WarehouseStore<>();

        store.addItem(new Product("1", "Laptop", "Electronics", 800));
        store.addItem(new Product("2", "Phone", "Electronics", 500));
        store.addItem(new Product("3", "Table", "Furniture", 200));

        System.out.println("ALL PRODUCTS:");c
        store.displayAll();

        System.out.println("\nFILTER ELECTRONICS:");
        System.out.println(store.findByCategory("Electronics"));

        store.removeItem("2");

        System.out.println("\nAFTER REMOVAL:");
        store.displayAll();
    }
}