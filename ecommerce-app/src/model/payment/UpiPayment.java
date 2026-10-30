package payment;

import model.PaymentStatus;

public class UpiPayment implements PaymentMethod {
    @Override
    public String getName() { return "UPI"; }

    @Override
    public PaymentStatus pay(double amount) {
        System.out.println("Paid Rs. " + amount + " via UPI");
        return PaymentStatus.PAID;
    }
}
