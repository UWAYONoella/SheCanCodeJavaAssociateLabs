import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class OrderBook {

    private Queue<Order> buyOrders =
            new ConcurrentLinkedQueue<>();

    private Queue<Order> sellOrders =
            new ConcurrentLinkedQueue<>();

    private ReentrantLock lock =
            new ReentrantLock();

    public void addOrder(Order order) {

        lock.lock();

        try {

            if (order.getType()
                    .equalsIgnoreCase("BUY")) {

                buyOrders.offer(order);

            } else {

                sellOrders.offer(order);
            }

        } finally {

            lock.unlock();
        }
    }

    public void matchOrders() {

        lock.lock();

        try {

            while (!buyOrders.isEmpty()
                    && !sellOrders.isEmpty()) {

                Order buy = buyOrders.poll();
                Order sell = sellOrders.poll();

                System.out.println(
                        "Matched -> "
                                + buy
                                + " <-> "
                                + sell
                );
            }

        } finally {

            lock.unlock();
        }
    }

    public MatchResult matchOrdersWithResult() {

        lock.lock();

        try {

            int matched = 0;

            while (!buyOrders.isEmpty()
                    && !sellOrders.isEmpty()) {

                buyOrders.poll();
                sellOrders.poll();

                matched++;
            }

            return new MatchResult(matched);

        } finally {

            lock.unlock();
        }
    }

    public int getBuyCount() {
        return buyOrders.size();
    }

    public int getSellCount() {
        return sellOrders.size();
    }
}