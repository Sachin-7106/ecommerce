package payment;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Coupon {
    private final String code;
    private final boolean percentage;
    private final double value;
    private final double maxDiscount;
    private final double minOrder;
    private final LocalDate expiry;
    private final Set<Integer> usedBy = new HashSet<>();

    public Coupon(String code, boolean percentage, double value, double maxDiscount, double minOrder, LocalDate expiry) {
        this.code = code;
        this.percentage = percentage;
        this.value = value;
        this.maxDiscount = maxDiscount;
        this.minOrder = minOrder;
        this.expiry = expiry;
    }

    public String getCode() { return code; }

    public boolean isValidFor(int customerId, double itemsTotal) {
        return !LocalDate.now().isAfter(expiry) && itemsTotal >= minOrder && !usedBy.contains(customerId);
    }

    public double discountFor(double itemsTotal) {
        double d = percentage ? itemsTotal * value / 100 : value;
        return Math.min(d, maxDiscount);
    }

    public void markUsed(int customerId) { usedBy.add(customerId); }
}
