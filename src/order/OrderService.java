package order;

import cart.Cart;
import model.Customer;
import model.PaymentStatus;
import payment.Coupon;
import payment.Payment;
import payment.PaymentMethod;
import payment.PaymentService;
import product.Product;
import store.DataStore;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private static int nextId = 1;
    private final PaymentService paymentService = new PaymentService();

    public Order placeOrder(Customer customer, PaymentMethod method, String couponCode) {
        Cart userCart = DataStore.carts.get(customer.getId());
        if (userCart == null || userCart.getItems().isEmpty()) throw new IllegalStateException("Cart is empty");

        // all items must be in stock
        List<String> outOfStock = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : userCart.getItems().entrySet()) {
            Product p = DataStore.products.get(e.getKey());
            if (!p.isActive() || p.getStock() < e.getValue()) outOfStock.add(p.getName());
        }
        if (!outOfStock.isEmpty()) {
            System.out.println("Order rejected. Out of stock: " + outOfStock);
            return null;
        }

        double itemsTotal = userCart.total();
        double delivery = itemsTotal < 500 ? 50 : 0;
        double discount = 0;
        Coupon coupon = null;
        if (couponCode != null) {
            coupon = DataStore.coupons.get(couponCode);
            if (coupon == null || !coupon.isValidFor(customer.getId(), itemsTotal)) {
                throw new IllegalStateException("Coupon not valid");
            }
            discount = coupon.discountFor(itemsTotal);
        }

        // payment first: if it fails, no order and no stock change
        Payment pay = paymentService.process(method, itemsTotal + delivery - discount);
        if (pay.getStatus() == PaymentStatus.FAILED) {
            System.out.println("Payment failed. Order not placed.");
            return null;
        }

        List<OrderItem> items = new ArrayList<>();
        Map<Integer, Shipment> shipments = new LinkedHashMap<>(); // one shipment per seller
        for (Map.Entry<Integer, Integer> e : userCart.getItems().entrySet()) {
            Product p = DataStore.products.get(e.getKey());
            p.setStock(p.getStock() - e.getValue());
            OrderItem item = new OrderItem(p.getId(), p.getSellerId(), p.getName(), p.getCategory(), p.getPrice(), e.getValue());
            items.add(item);
            shipments.computeIfAbsent(p.getSellerId(), Shipment::new).add(item);
        }

        if (coupon != null) coupon.markUsed(customer.getId());
        Order order = new Order(nextId++, customer.getId(), items, shipments, itemsTotal, delivery, discount, pay);
        DataStore.orders.put(order.getId(), order);
        DataStore.payments.put(pay.getId(), pay);
        userCart.clear();
        System.out.println("Placed: " + order);
        return order;
    }

    public void cancel(Order order) {
        if (!order.canCancel()) throw new IllegalStateException("Cannot cancel after shipping");
        for (OrderItem i : order.getItems()) {
            Product p = DataStore.products.get(i.getProductId());
            p.setStock(p.getStock() + i.getQuantity());
        }
        order.markCancelled();
        if (order.getPayment().getStatus() == PaymentStatus.PAID) order.getPayment().setStatus(PaymentStatus.REFUNDED);
    }
}
