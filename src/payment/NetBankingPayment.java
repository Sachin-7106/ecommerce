package payment;

import model.PaymentStatus;

public class NetBankingPayment implements PaymentMethod {
    @Override
    public String getName() { return "Net Banking"; }

    @Override
    public PaymentStatus pay(double amount) {
        System.out.println("Paid Rs. " + amount + " via Net Banking");
        return PaymentStatus.PAID;
    }
}
