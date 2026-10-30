package payment;

import model.PaymentStatus;

public class CodPayment implements PaymentMethod {
    private static final double MAX_COD_AMOUNT = 5000;

    @Override
    public String getName() { return "COD"; }

    @Override
    public PaymentStatus pay(double amount) {
        if (amount > MAX_COD_AMOUNT) {
            System.out.println("COD not allowed above Rs. " + MAX_COD_AMOUNT);
            return PaymentStatus.FAILED;
        }
        return PaymentStatus.PENDING; // paid on delivery
    }
}
