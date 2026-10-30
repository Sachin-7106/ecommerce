package payment;

import model.PaymentStatus;

public class CardPayment implements PaymentMethod {
    @Override
    public String getName() { return "Card"; }

    @Override
    public PaymentStatus pay(double amount) {
        System.out.println("Paid Rs. " + amount + " via Card");
        return PaymentStatus.PAID;
    }
}
