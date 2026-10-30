package order;

import model.Category;

public class OrderItem {
    private final int productId;
    private final int sellerId;
    private final String name;
    private final Category category;
    private final double price; // price at the time of ordering
    private final int quantity;

    public OrderItem(int productId, int sellerId, String name, Category category, double price, int quantity) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductId() { return productId; }
    public int getSellerId() { return sellerId; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public double lineTotal() { return price * quantity; }
}
