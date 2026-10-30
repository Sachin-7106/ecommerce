import cart.Cart;
import model.*;
import order.*;
import payment.*;
import product.*;
import returns.*;
import store.DataStore;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Admin admin = new Admin(1, "Admin", "admin@shop.com");
        Seller ravi = new Seller(2, "Ravi", "ravi@shop.com");
        Seller anu = new Seller(4, "Anu", "anu@shop.com");
        Customer jeni = new Customer(3, "Jeni", "jeni@mail.com");
        jeni.addAddress("12 Main St, Trichy", true);
        for (User u : new User[]{admin, ravi, anu, jeni}) DataStore.users.put(u.getId(), u);

        ProductService productService = new ProductService();
        productService.addProduct(ravi, new ElectronicsProduct(101, "Headphones", 2, 1200, 10, 12));
        productService.addProduct(anu, new FashionProduct(102, "T-Shirt", 4, 400, 5, "M"));
        try {
            productService.addProduct(jeni, new FashionProduct(103, "Cap", 4, 100, 5, "L"));
        } catch (IllegalStateException e) {
            System.out.println("Blocked: " + e.getMessage());
        }

        DataStore.coupons.put("SAVE10", new Coupon("SAVE10", true, 10, 200, 500, LocalDate.now().plusDays(30)));

        Cart myCart = new Cart();
        DataStore.carts.put(jeni.getId(), myCart);
        myCart.add(DataStore.products.get(101), 1);
        myCart.add(DataStore.products.get(102), 2);

        OrderService orderService = new OrderService();
        Order o = orderService.placeOrder(jeni, new UpiPayment(), "SAVE10");

        // move every shipment through Packed -> Shipped -> Out for Delivery -> Delivered
        for (int i = 0; i < 4; i++) {
            for (int sellerId : o.getShipments().keySet()) o.advance(sellerId);
        }
        System.out.println(o);

        new ReviewService().addReview(o, 101, 5, "Great sound");
        ReturnService returnService = new ReturnService();
        ReturnRequest r = returnService.requestReturn(o, 102);
        returnService.approve(ravi, r);
        System.out.println("T-Shirt stock after return: " + DataStore.products.get(102).getStock());
    }
}
