package order;

import model.OrderStatus;
import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private final int sellerId;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.PLACED;

    public Shipment(int sellerId) { this.sellerId = sellerId; }

    public void add(OrderItem item) { items.add(item); }
    public int getSellerId() { return sellerId; }
    public OrderStatus getStatus() { return status; }

    // moves exactly one step forward, so skipping a step is impossible
    public void advance() {
        if (status == OrderStatus.DELIVERED) throw new IllegalStateException("Already delivered");
        status = OrderStatus.values()[status.ordinal() + 1];
    }
}
