package returns;

public class Review {
    private final int orderId;
    private final int productId;
    private final int rating;
    private final String comment;

    public Review(int orderId, int productId, int rating, String comment) {
        this.orderId = orderId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getOrderId() { return orderId; }
    public int getProductId() { return productId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
}
