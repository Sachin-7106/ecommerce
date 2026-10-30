package cart;

import product.Product;
import store.DataStore;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<Integer, Integer> items = new LinkedHashMap<>(); // productId -> quantity

    public void add(Product p, int qty) {
        if (!p.isActive() || p.isOutOfStock()) throw new IllegalStateException(p.getName() + " is out of stock");
        int newQty = items.getOrDefault(p.getId(), 0) + qty;
        if (newQty > 10) throw new IllegalStateException("Max 10 units of one product");
        if (newQty > p.getStock()) throw new IllegalStateException("Only " + p.getStock() + " units available");
        if (!items.containsKey(p.getId()) && items.size() >= 20) throw new IllegalStateException("Max 20 different products");
        items.put(p.getId(), newQty);
    }

    public Map<Integer, Integer> getItems() { return items; }

    // always uses the latest product price
    public double total() {
        double sum = 0;
        for (Map.Entry<Integer, Integer> e : items.entrySet()) {
            sum += DataStore.products.get(e.getKey()).getPrice() * e.getValue();
        }
        return sum;
    }

    public void clear() { items.clear(); }
}
