package product;

import model.Customer;
import model.User;
import store.DataStore;

public class ProductService {

    public void addProduct(User user, Product product) {
        if (user instanceof Customer) {
            throw new IllegalStateException("Only Admin or Seller can add products");
        }
        DataStore.products.put(product.getId(), product);
        System.out.println("Added: " + product);
    }

    public boolean canAddToCart(Product product) {
        return product.isActive() && !product.isOutOfStock();
    }

    // Products are never deleted, only marked inactive
    public void deactivate(int productId) {
        Product p = DataStore.products.get(productId);
        if (p != null) p.markInactive();
    }

    // New price applies to new carts/orders only (orders store their own price)
    public void updatePrice(int productId, double newPrice) {
        Product p = DataStore.products.get(productId);
        if (p != null) p.setPrice(newPrice);
    }
}
