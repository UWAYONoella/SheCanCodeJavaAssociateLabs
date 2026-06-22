// lab2_2_design_patterns/src/PaymentStrategy.java
/**
 * EXERCISE 2.5: Strategy Pattern - Interface
 *
 * This defines the contract for all payment strategies.
 * All payment methods must implement this interface.
 *
 * WHY STRATEGY PATTERN?
 * 1. Swap payment methods at runtime
 * 2. Easy to add new payment methods
 * 3. Each strategy has its own logic
 */
public interface PaymentStrategy {
    PaymentResult process(PaymentRequest request);
}