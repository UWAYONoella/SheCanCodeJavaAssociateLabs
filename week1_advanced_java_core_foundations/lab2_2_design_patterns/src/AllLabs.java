import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// ============================================================
// PART 1: LAB 2.1 - STREAMS (Included for completeness)
// ============================================================

class Product {
    private String id;
    private String name;
    private String category;
    private double price;
    private int stock;

    public Product(String id, String name, String category, double price, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    public String toString() {
        return "Product{id=" + id + ", name=" + name + ", price=" + price + "}";
    }
}

class LineItem {
    private String productId;
    private String category;
    private int quantity;
    private double price;

    public LineItem(String productId, String category, int quantity, double price) {
        this.productId = productId;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() { return productId; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setProductId(String productId) { this.productId = productId; }
    public void setCategory(String category) { this.category = category; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }

    public double getRevenue() {
        return quantity * price;
    }

    public String toString() {
        return "LineItem{productId=" + productId + ", quantity=" + quantity +
                ", revenue=" + getRevenue() + "}";
    }
}

class Order {
    private String orderId;
    private String customerId;
    private String status;
    private List<LineItem> items;

    public Order(String orderId, String customerId, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.items = new ArrayList<>();
    }

    public void addItem(LineItem item) {
        items.add(item);
    }

    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public List<LineItem> getItems() { return items; }

    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setStatus(String status) { this.status = status; }
    public void setItems(List<LineItem> items) { this.items = items; }

    public boolean isDelivered() {
        return "DELIVERED".equals(status);
    }

    public String toString() {
        return "Order{id=" + orderId + ", status=" + status + ", items=" + items.size() + "}";
    }
}

class RevenueReport {
    private final double totalRevenue;
    private final int itemCount;
    private final double maxSingleItemRevenue;

    public RevenueReport(double totalRevenue, int itemCount, double maxSingleItemRevenue) {
        this.totalRevenue = totalRevenue;
        this.itemCount = itemCount;
        this.maxSingleItemRevenue = maxSingleItemRevenue;
    }

    public double getTotalRevenue() { return totalRevenue; }
    public int getItemCount() { return itemCount; }
    public double getMaxSingleItemRevenue() { return maxSingleItemRevenue; }

    public String toString() {
        return "RevenueReport{totalRevenue=" + totalRevenue + ", itemCount=" + itemCount +
                ", maxRevenue=" + maxSingleItemRevenue + "}";
    }
}

class StreamService {
    public List<LineItem> getAllLineItems(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toList());
    }

    public double calculateBulkRevenue(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .filter(item -> item.getQuantity() > 5)
                .map(LineItem::getRevenue)
                .reduce(0.0, Double::sum);
    }

    public List<Map.Entry<String, Double>> getTopProducts(List<Order> orders, int n) {
        Map<String, Double> productRevenue = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        LineItem::getProductId,
                        Collectors.summingDouble(LineItem::getRevenue)
                ));

        return productRevenue.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(n)
                .collect(Collectors.toList());
    }

    public Map<String, Long> countItemsByCategory(List<LineItem> items) {
        return items.stream()
                .collect(Collectors.groupingBy(
                        LineItem::getCategory,
                        Collectors.counting()
                ));
    }

    public Map<Boolean, List<Order>> partitionOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.partitioningBy(Order::isDelivered));
    }

    public Map<String, Double> getProductAveragePrices(List<LineItem> items) {
        return items.stream()
                .collect(Collectors.toMap(
                        LineItem::getProductId,
                        LineItem::getPrice,
                        (price1, price2) -> (price1 + price2) / 2
                ));
    }

    public static class RevenueReportCollector
            implements Collector<LineItem, double[], RevenueReport> {

        public Supplier<double[]> supplier() {
            return () -> new double[]{0.0, 0.0, 0.0};
        }

        public BiConsumer<double[], LineItem> accumulator() {
            return (acc, item) -> {
                double revenue = item.getRevenue();
                acc[0] += revenue;
                acc[1] += 1;
                acc[2] = Math.max(acc[2], revenue);
            };
        }

        public BinaryOperator<double[]> combiner() {
            return (acc1, acc2) -> {
                acc1[0] += acc2[0];
                acc1[1] += acc2[1];
                acc1[2] = Math.max(acc1[2], acc2[2]);
                return acc1;
            };
        }

        public Function<double[], RevenueReport> finisher() {
            return acc -> new RevenueReport(acc[0], (int) acc[1], acc[2]);
        }

        public Set<Collector.Characteristics> characteristics() {
            return Set.of(Collector.Characteristics.UNORDERED);
        }
    }

    public RevenueReport generateRevenueReportWithCollector(List<LineItem> items) {
        return items.stream().collect(new RevenueReportCollector());
    }

    public List<Map.Entry<String, Double>> getTopProductsParallel(List<Order> orders, int n) {
        Map<String, Double> productRevenue = orders.parallelStream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        LineItem::getProductId,
                        Collectors.summingDouble(LineItem::getRevenue)
                ));

        return productRevenue.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Order> generateLargeDataset(int orderCount, int itemsPerOrder) {
        List<Order> orders = new ArrayList<>();
        Random random = new Random();
        String[] categories = {"Electronics", "Books", "Clothing", "Food", "Toys"};

        for (int i = 0; i < orderCount; i++) {
            String status = random.nextBoolean() ? "DELIVERED" : "PENDING";
            Order order = new Order("O" + i, "C" + i, status);

            for (int j = 0; j < itemsPerOrder; j++) {
                String productId = "P" + random.nextInt(100);
                String category = categories[random.nextInt(categories.length)];
                int quantity = random.nextInt(10) + 1;
                double price = random.nextDouble() * 100 + 10;
                order.addItem(new LineItem(productId, category, quantity, price));
            }
            orders.add(order);
        }
        return orders;
    }

    public void benchmarkStreams(List<Order> orders, int n) {
        System.out.println();
        System.out.println("Benchmarking with " + orders.size() + " orders...");

        long start = System.nanoTime();
        List<Map.Entry<String, Double>> seqResult = getTopProducts(orders, n);
        long seqTime = System.nanoTime() - start;

        start = System.nanoTime();
        List<Map.Entry<String, Double>> parResult = getTopProductsParallel(orders, n);
        long parTime = System.nanoTime() - start;

        boolean match = true;
        if (seqResult.size() == parResult.size()) {
            for (int i = 0; i < seqResult.size(); i++) {
                if (!seqResult.get(i).getKey().equals(parResult.get(i).getKey())) {
                    match = false;
                    break;
                }
            }
        } else {
            match = false;
        }

        System.out.println("  Sequential time: " + seqTime / 1_000_000 + " ms");
        System.out.println("  Parallel time:   " + parTime / 1_000_000 + " ms");
        System.out.println("  Results match:   " + (match ? "YES" : "NO"));

        if (parTime < seqTime) {
            System.out.println("  Parallel was faster!");
        } else {
            System.out.println("  Sequential was faster for this dataset");
        }
    }
}

// ============================================================
// PART 2: LAB 2.2 - DESIGN PATTERNS
// ============================================================

// ---------- Exercise 2.4: Builder Pattern ----------
class NotificationMessage {

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    private final String recipient;
    private final String subject;
    private final String body;
    private final Priority priority;
    private final List<String> attachments;

    private NotificationMessage(Builder builder) {
        this.recipient = builder.recipient;
        this.subject = builder.subject;
        this.body = builder.body;
        this.priority = builder.priority != null ? builder.priority : Priority.MEDIUM;
        this.attachments = builder.attachments != null ?
                new ArrayList<>(builder.attachments) : new ArrayList<>();
    }

    public String getRecipient() { return recipient; }
    public String getSubject() { return subject; }
    public String getBody() { return body; }
    public Priority getPriority() { return priority; }
    public List<String> getAttachments() { return attachments; }

    public String toString() {
        return "Notification{to=" + recipient + ", priority=" + priority +
                ", body=" + body.substring(0, Math.min(15, body.length())) + "...}";
    }

    public static class Builder {
        private String recipient;
        private String subject;
        private String body;
        private Priority priority;
        private List<String> attachments;

        public Builder to(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder attach(String attachment) {
            if (this.attachments == null) {
                this.attachments = new ArrayList<>();
            }
            this.attachments.add(attachment);
            return this;
        }

        public NotificationMessage build() {
            if (recipient == null || recipient.trim().isEmpty()) {
                throw new IllegalStateException("Recipient is required!");
            }
            if (body == null || body.trim().isEmpty()) {
                throw new IllegalStateException("Body is required!");
            }
            return new NotificationMessage(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

// ---------- Exercise 2.5: Strategy Pattern ----------
interface PaymentStrategy {
    PaymentResult process(PaymentRequest request);
}

class PaymentRequest {
    private final String orderId;
    private final double amount;
    private final String currency;
    private final String paymentMethod;
    private final String accountInfo;

    public PaymentRequest(String orderId, double amount, String currency,
                          String paymentMethod, String accountInfo) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.accountInfo = accountInfo;
    }

    public String getOrderId() { return orderId; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getAccountInfo() { return accountInfo; }

    public String toString() {
        return "PaymentRequest{orderId=" + orderId + ", amount=" + amount + ", method=" + paymentMethod + "}";
    }
}

class PaymentResult {
    private final boolean success;
    private final String message;
    private final String transactionId;
    private final double fee;
    private final double totalAmount;

    public PaymentResult(boolean success, String message, String transactionId,
                         double fee, double totalAmount) {
        this.success = success;
        this.message = message;
        this.transactionId = transactionId;
        this.fee = fee;
        this.totalAmount = totalAmount;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getTransactionId() { return transactionId; }
    public double getFee() { return fee; }
    public double getTotalAmount() { return totalAmount; }

    public String toString() {
        return "PaymentResult{success=" + success + ", message=" + message +
                ", transactionId=" + transactionId + ", fee=" + fee + ", total=" + totalAmount + "}";
    }
}

class CreditCardStrategy implements PaymentStrategy {
    private static final double FEE_RATE = 0.025;

    public PaymentResult process(PaymentRequest request) {
        String cardNumber = request.getAccountInfo();

        if (cardNumber == null || !cardNumber.startsWith("4")) {
            return new PaymentResult(false, "Invalid card number. Must start with 4", null, 0, 0);
        }

        double fee = request.getAmount() * FEE_RATE;
        double total = request.getAmount() + fee;
        String transactionId = "CC-" + System.currentTimeMillis();

        return new PaymentResult(true, "Credit card payment successful", transactionId, fee, total);
    }
}

class BankTransferStrategy implements PaymentStrategy {
    private static final double FEE_RATE = 0.01;

    public PaymentResult process(PaymentRequest request) {
        String accountNumber = request.getAccountInfo();

        if (accountNumber == null || accountNumber.length() < 10) {
            return new PaymentResult(false, "Account number must be at least 10 characters", null, 0, 0);
        }

        double fee = request.getAmount() * FEE_RATE;
        double total = request.getAmount() + fee;
        String transactionId = "BT-" + System.currentTimeMillis();

        return new PaymentResult(true, "Bank transfer successful", transactionId, fee, total);
    }
}

class MobileMoneyStrategy implements PaymentStrategy {
    private static final double FEE_RATE = 0.015;

    public PaymentResult process(PaymentRequest request) {
        String phoneNumber = request.getAccountInfo();

        if (phoneNumber == null || !phoneNumber.startsWith("07")) {
            return new PaymentResult(false, "Phone number must start with 07", null, 0, 0);
        }

        double fee = request.getAmount() * FEE_RATE;
        double total = request.getAmount() + fee;
        String transactionId = "MM-" + System.currentTimeMillis();

        return new PaymentResult(true, "Mobile money payment successful", transactionId, fee, total);
    }
}

class PaymentProcessor {
    private PaymentStrategy strategy;

    public PaymentProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public PaymentResult process(PaymentRequest request) {
        if (strategy == null) {
            return new PaymentResult(false, "No payment strategy set", null, 0, 0);
        }
        return strategy.process(request);
    }
}

// ---------- Exercise 2.6: Observer Pattern ----------
enum OrderEvent {
    ORDER_PLACED,
    ORDER_SHIPPED,
    ORDER_DELIVERED,
    ORDER_CANCELLED
}

class OrderPattern {
    private String id;
    private String customer;
    private double amount;
    private String status;

    public OrderPattern(String id, String customer, double amount) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.status = "PLACED";
    }

    public String getId() { return id; }
    public String getCustomer() { return customer; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toString() {
        return "Order{id=" + id + ", customer=" + customer + ", amount=" + amount + "}";
    }
}

interface OrderObserver {
    void update(OrderPattern order, OrderEvent event);
}

class EmailNotifier implements OrderObserver {
    public void update(OrderPattern order, OrderEvent event) {
        System.out.println("  [EMAIL] Order " + order.getId() + " is now " + event +
                ". Customer: " + order.getCustomer());
    }
}

class InventoryUpdater implements OrderObserver {
    public void update(OrderPattern order, OrderEvent event) {
        if (event == OrderEvent.ORDER_PLACED) {
            System.out.println("  [INVENTORY] Order " + order.getId() +
                    " placed. Updating stock levels.");
        } else if (event == OrderEvent.ORDER_CANCELLED) {
            System.out.println("  [INVENTORY] Order " + order.getId() +
                    " cancelled. Restoring stock levels.");
        } else {
            System.out.println("  [INVENTORY] Order " + order.getId() +
                    " - " + event + " (No inventory change)");
        }
    }
}

class AuditLogger implements OrderObserver {
    public void update(OrderPattern order, OrderEvent event) {
        System.out.println("  [AUDIT] Order " + order.getId() +
                " - Event: " + event +
                " - Amount: $" + order.getAmount());
    }
}

class OrderEventBus {
    private List<OrderObserver> observers = new ArrayList<>();

    public void subscribe(OrderObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer subscribed: " + observer.getClass().getSimpleName());
        }
    }

    public void unsubscribe(OrderObserver observer) {
        observers.remove(observer);
        System.out.println("Observer unsubscribed: " + observer.getClass().getSimpleName());
    }

    public void publish(OrderPattern order, OrderEvent event) {
        System.out.println();
        System.out.println("Publishing: " + event + " for order " + order.getId());
        System.out.println("   Notifying " + observers.size() + " observers...");
        System.out.println();

        for (OrderObserver observer : observers) {
            observer.update(order, event);
        }
    }
}

// ============================================================
// MAIN CLASS
// ============================================================
public class AllLabs {

    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("     LAB 2.1 & 2.2: COMPLETE DEMONSTRATION     ");
        System.out.println("================================================");
        System.out.println();

        // ============================================================
        // PART 1: LAB 2.1 - STREAMS
        // ============================================================
        System.out.println("================================================");
        System.out.println("            LAB 2.1: JAVA STREAMS             ");
        System.out.println("================================================");
        System.out.println();

        runStreamsLab();

        // ============================================================
        // PART 2: LAB 2.2 - DESIGN PATTERNS
        // ============================================================
        System.out.println();
        System.out.println("================================================");
        System.out.println("          LAB 2.2: DESIGN PATTERNS            ");
        System.out.println("================================================");
        System.out.println();

        runDesignPatternsLab();

        System.out.println();
        System.out.println("================================================");
        System.out.println("     ALL LABS COMPLETED SUCCESSFULLY!         ");
        System.out.println("================================================");
    }

    // ============================================================
    // RUN STREAMS LAB
    // ============================================================
    private static void runStreamsLab() {
        StreamService service = new StreamService();
        List<Order> orders = createSampleOrders();
        List<LineItem> allItems = service.getAllLineItems(orders);

        System.out.println("EXERCISE 2.1: Stream Pipeline");
        System.out.println("------------------------------------------------");

        System.out.println("Task 1: Get all line items (flatMap)");
        System.out.println("  Total items: " + allItems.size());

        System.out.println();
        System.out.println("Task 2: Bulk revenue (filter + map + reduce)");
        System.out.println("  Total: $" + service.calculateBulkRevenue(orders));

        System.out.println();
        System.out.println("Task 3: Top 2 products (groupingBy)");
        service.getTopProducts(orders, 2).forEach(entry ->
                System.out.println("  Product " + entry.getKey() + ": $" + entry.getValue()));

        System.out.println();
        System.out.println("EXERCISE 2.2: Collectors & Grouping");
        System.out.println("------------------------------------------------");

        System.out.println("Task 1: Items per category (groupingBy)");
        service.countItemsByCategory(allItems).forEach((cat, count) ->
                System.out.println("  " + cat + ": " + count + " items"));

        System.out.println();
        System.out.println("Task 2: Order partition (partitioningBy)");
        Map<Boolean, List<Order>> partitioned = service.partitionOrders(orders);
        System.out.println("  Delivered: " + partitioned.get(true).size() + " orders");
        System.out.println("  Pending: " + partitioned.get(false).size() + " orders");

        System.out.println();
        System.out.println("Task 3: Product average prices (toMap)");
        service.getProductAveragePrices(allItems).forEach((id, price) ->
                System.out.println("  Product " + id + ": $" + price));

        System.out.println();
        System.out.println("EXERCISE 2.3: Custom Collector & Parallel Streams");
        System.out.println("------------------------------------------------");

        System.out.println("Task 1: Custom Collector (one pass)");
        RevenueReport report = service.generateRevenueReportWithCollector(allItems);
        System.out.println("  " + report);

        System.out.println();
        System.out.println("Task 2 & 3: Parallel streams & benchmarking");
        service.benchmarkStreams(orders, 2);

        System.out.println();
        System.out.println("Large dataset (10,000 orders)");
        List<Order> largeOrders = service.generateLargeDataset(10000, 5);
        service.benchmarkStreams(largeOrders, 10);

        System.out.println();
        System.out.println("LAB 2.1 COMPLETED");
    }

    private static List<Order> createSampleOrders() {
        List<Order> orders = new ArrayList<>();

        Order o1 = new Order("O001", "C001", "DELIVERED");
        o1.addItem(new LineItem("P001", "Electronics", 3, 100.0));
        o1.addItem(new LineItem("P002", "Electronics", 10, 50.0));
        o1.addItem(new LineItem("P003", "Clothing", 2, 30.0));
        orders.add(o1);

        Order o2 = new Order("O002", "C002", "PENDING");
        o2.addItem(new LineItem("P001", "Electronics", 5, 100.0));
        o2.addItem(new LineItem("P004", "Books", 7, 20.0));
        orders.add(o2);

        Order o3 = new Order("O003", "C003", "DELIVERED");
        o3.addItem(new LineItem("P002", "Electronics", 2, 50.0));
        o3.addItem(new LineItem("P005", "Books", 1, 15.0));
        orders.add(o3);

        return orders;
    }

    // ============================================================
    // RUN DESIGN PATTERNS LAB
    // ============================================================
    private static void runDesignPatternsLab() {
        System.out.println("EXERCISE 2.4: Builder Pattern");
        System.out.println("------------------------------------------------");

        NotificationMessage notification = NotificationMessage.builder()
                .to("john@email.com")
                .subject("Order Confirmation")
                .body("Your order has been confirmed!")
                .priority(NotificationMessage.Priority.HIGH)
                .attach("invoice.pdf")
                .attach("receipt.pdf")
                .build();

        System.out.println("Created: " + notification);
        System.out.println("  Recipient: " + notification.getRecipient());
        System.out.println("  Subject: " + notification.getSubject());
        System.out.println("  Priority: " + notification.getPriority());
        System.out.println("  Attachments: " + notification.getAttachments());

        System.out.println();
        System.out.println("Testing validation (should fail):");
        try {
            NotificationMessage invalid = NotificationMessage.builder()
                    .to("")
                    .body("Hello")
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("  OK: Caught expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("EXERCISE 2.5: Strategy Pattern");
        System.out.println("------------------------------------------------");

        PaymentProcessor processor = new PaymentProcessor(new CreditCardStrategy());

        System.out.println("Using Credit Card:");
        PaymentRequest ccRequest = new PaymentRequest("ORD-001", 100.00, "USD",
                "Credit Card", "4111111111111111");
        System.out.println("  " + processor.process(ccRequest));

        System.out.println();
        System.out.println("Switching to Bank Transfer:");
        processor.setStrategy(new BankTransferStrategy());
        PaymentRequest btRequest = new PaymentRequest("ORD-002", 100.00, "USD",
                "Bank Transfer", "1234567890");
        System.out.println("  " + processor.process(btRequest));

        System.out.println();
        System.out.println("Switching to Mobile Money:");
        processor.setStrategy(new MobileMoneyStrategy());
        PaymentRequest mmRequest = new PaymentRequest("ORD-003", 100.00, "USD",
                "Mobile Money", "0712345678");
        System.out.println("  " + processor.process(mmRequest));

        System.out.println();
        System.out.println("Testing invalid payment:");
        PaymentRequest invalidRequest = new PaymentRequest("ORD-004", 100.00, "USD",
                "Mobile Money", "12345");
        System.out.println("  " + processor.process(invalidRequest));

        System.out.println();
        System.out.println("EXERCISE 2.6: Observer Pattern");
        System.out.println("------------------------------------------------");

        OrderEventBus eventBus = new OrderEventBus();

        EmailNotifier email = new EmailNotifier();
        InventoryUpdater inventory = new InventoryUpdater();
        AuditLogger audit = new AuditLogger();

        eventBus.subscribe(email);
        eventBus.subscribe(inventory);
        eventBus.subscribe(audit);

        OrderPattern order = new OrderPattern("ORD-001", "John Doe", 150.00);

        eventBus.publish(order, OrderEvent.ORDER_PLACED);
        eventBus.publish(order, OrderEvent.ORDER_SHIPPED);
        eventBus.publish(order, OrderEvent.ORDER_DELIVERED);

        eventBus.unsubscribe(email);

        System.out.println();
        System.out.println("After unsubscribing EmailNotifier:");
        eventBus.publish(order, OrderEvent.ORDER_CANCELLED);

        System.out.println();
        System.out.println("LAB 2.2 COMPLETED");
    }
}