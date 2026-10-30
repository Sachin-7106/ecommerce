package product;

import model.Category;

public class FashionProduct extends Product {
    private final String size;

    public FashionProduct(int id, String name, int sellerId, double price, int stock, String size) {
        super(id, name, Category.FASHION, sellerId, price, stock);
        this.size = size;
    }

    @Override
    public String getDetails() { return "Size " + size; }
}
