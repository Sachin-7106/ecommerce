package order;

import model.OrderStatus;
import model.PaymentStatus;
import payment.Payment;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Order {
    private final int id;
    private final int customerId;
    private final List<OrderItem> items;
    private final Map<Integer, Shipment> shipments; // sellerId -> shipment
    private final double itemsTotal;
    private final double deliveryCharge;
    private final double discount;
    private final Payment payment;
    private boolean cancelled;
    private LocalDate deliveredDate;

    public Order(int id, int customerId, List<OrderItem> items, Map<Integer, Shipment> shipments,
                 double itemsTotal, double deliveryCharge, double discount, Payment payment) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.shipments = shipments;
        this.itemsTotal = itemsTotal;
        this.deliveryCharge = deliveryCharge;
        this.discount = discount;
        this.payment = payment;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; }
    public Map<Integer, Shipment> getShipments() { return shipments; }
    public Payment getPayment() { return payment; }
    public LocalDate getDeliveredDate() { return deliveredDate; }
    public double getTotal() { return itemsTotal + deliveryCharge - discount; }

    // Order is Delivered only when ALL shipments are delivered
    public OrderStatus getStatus() {
        if (cancelled) return OrderStatus.CANCELLED;
        OrderStatus min = OrderStatus.DELIVERED;
        for (Shipment s : shipments.values()) {
            if (s.getStatus().ordinal() < min.ordinal()) min = s.getStatus();
        }
        return min;
    }

    public void advance(int sellerId) {
        if (cancelled) throw new IllegalStateException("Order is cancelled");
        shipments.get(sellerId).advance();
        if (getStatus() == OrderStatus.DELIVERED) {
            deliveredDate = LocalDate.now();
            if (payment.getStatus() == PaymentStatus.PENDING) payment.setStatus(PaymentStatus.PAID); // COD
        }
    }

    public boolean canCancel() {
        if (cancelled) return false;
        for (Shipment s : shipments.values()) {
            if (s.getStatus().ordinal() >= OrderStatus.SHIPPED.ordinal()) return false;
        }
        return true;
    }

    public void markCancelled() { cancelled = true; }

    public OrderItem findItem(int productId) {
        for (OrderItem i : items) if (i.getProductId() == productId) return i;
        return null;
    }

    @Override
    public String toString() { return "Order #" + id + " | " + getStatus() + " | Rs." + getTotal(); }
}
