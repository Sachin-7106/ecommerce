package returns;

import model.Category;
import model.Customer;
import model.OrderStatus;
import model.PaymentStatus;
import model.User;
import order.Order;
import order.OrderItem;
import product.Product;
import store.DataStore;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnService {

    public ReturnRequest requestReturn(Order order, int productId) {
        if (order.getStatus() != OrderStatus.DELIVERED) throw new IllegalStateException("Order not delivered yet");
        if (ChronoUnit.DAYS.between(order.getDeliveredDate(), LocalDate.now()) > 7) {
            throw new IllegalStateException("Return window (7 days) is over");
        }
        OrderItem item = order.findItem(productId);
        if (item == null) throw new IllegalArgumentException("Product not in this order");
        if (item.getCategory() == Category.GROCERY) throw new IllegalStateException("Grocery items cannot be returned");
        return new ReturnRequest(order, item);
    }

    public void approve(User approver, ReturnRequest request) {
        if (approver instanceof Customer) throw new IllegalStateException("Only Seller or Admin can approve returns");
        Product p = DataStore.products.get(request.getItem().getProductId());
        p.setStock(p.getStock() + request.getItem().getQuantity());
        request.getOrder().getPayment().setStatus(PaymentStatus.REFUNDED);
        request.markApproved();
        System.out.println("Return approved. Refund Rs." + request.getRefundAmount() + " (delivery charge not refunded)");
    }
}
