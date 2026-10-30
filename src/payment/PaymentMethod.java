package payment;

import model.PaymentStatus;

public interface PaymentMethod {
    String getName();
    PaymentStatus pay(double amount);
}
