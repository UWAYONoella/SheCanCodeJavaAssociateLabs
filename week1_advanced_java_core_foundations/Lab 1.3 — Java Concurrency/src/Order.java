public class Order {

    private String orderId;
    private String type; // BUY or SELL
    private int quantity;

    public Order(String orderId,
                 String type,
                 int quantity) {

        this.orderId = orderId;
        this.type = type;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return orderId +
                " | " +
                type +
                " | " +
                quantity;
    }
}