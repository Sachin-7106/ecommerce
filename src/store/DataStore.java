package store;

import cart.Cart;
import model.User;
import order.Order;
import payment.Coupon;
import payment.Payment;
import product.Product;
import returns.Review;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    public static final Map<Integer, User> users = new HashMap<>();
    public static final Map<Integer, Product> products = new HashMap<>();
    public static final Map<Integer, Cart> carts = new HashMap<>();          // customerId -> cart
    public static final Map<Integer, Order> orders = new HashMap<>();
    public static final Map<Integer, Payment> payments = new HashMap<>();
    public static final Map<String, Coupon> coupons = new HashMap<>();
    public static final List<Review> reviews = new ArrayList<>();
}
