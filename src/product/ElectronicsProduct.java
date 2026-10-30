package product;

import model.Category;

public class ElectronicsProduct extends Product {
    private final int warrantyMonths;

    public ElectronicsProduct(int id, String name, int sellerId, double price, int stock, int warrantyMonths) {
        super(id, name, Category.ELECTRONICS, sellerId, price, stock);
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String getDetails() { return warrantyMonths + " months warranty"; }
}
