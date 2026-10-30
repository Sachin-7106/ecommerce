package returns;

import model.OrderStatus;
import order.Order;
import store.DataStore;

public class ReviewService {

    public void addReview(Order order, int productId, int rating, String comment) {
        if (order.getStatus() != OrderStatus.DELIVERED) throw new IllegalStateException("Review only after delivery");
        if (order.findItem(productId) == null) throw new IllegalArgumentException("Product not in this order");
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1 to 5");
        for (Review r : DataStore.reviews) {
            if (r.getOrderId() == order.getId() && r.getProductId() == productId) {
                throw new IllegalStateException("Already reviewed this product for this order");
            }
        }
        DataStore.reviews.add(new Review(order.getId(), productId, rating, comment));
        flagIfLow(productId);
    }

    private void flagIfLow(int productId) {
        int count = 0;
        double sum = 0;
        for (Review r : DataStore.reviews) {
            if (r.getProductId() == productId) { count++; sum += r.getRating(); }
        }
        if (count >= 10 && sum / count < 2.0) System.out.println("Product " + productId + " flagged for admin review");
    }
}
