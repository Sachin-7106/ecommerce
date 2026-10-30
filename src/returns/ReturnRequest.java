package returns;

import order.Order;
import order.OrderItem;

public class ReturnRequest {
    private final Order order;
    private final OrderItem item;
    private boolean approved;

    public ReturnRequest(Order order, OrderItem item) {
        this.order = order;
        this.item = item;
    }

    public Order getOrder() { return order; }
    public OrderItem getItem() { return item; }
    public boolean isApproved() { return approved; }
    public void markApproved() { approved = true; }
    public double getRefundAmount() { return item.lineTotal(); } // delivery charge not refunded
}
