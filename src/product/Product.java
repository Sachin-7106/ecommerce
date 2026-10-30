package product;

import model.Category;

public abstract class Product {
    private final int id;
    private final String name;
    private final Category category;
    private final int sellerId;
    private double price;
    private int stock;
    private boolean active = true;

    protected Product(int id, String name, Category category, int sellerId, double price, int stock) {
        if (price <= 0) throw new IllegalArgumentException("Price must be greater than 0");
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative");
        this.id = id;
        this.name = name;
        this.category = category;
        this.sellerId = sellerId;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getSellerId() { return sellerId; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public boolean isActive() { return active; }

    public void setPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Price must be greater than 0");
        this.price = price;
    }
    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative");
        this.stock = stock;
    }
    public void markInactive() { this.active = false; }
    public boolean isOutOfStock() { return stock == 0; }

    public abstract String getDetails();

    @Override
    public String toString() {
        return name + " | Rs." + price + " | " + (isOutOfStock() ? "Out of Stock" : stock + " in stock") + " | " + getDetails();
    }
}
