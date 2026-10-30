package payment;

public class PaymentService {
    private static int nextId = 1;

    // Polymorphism: works with any PaymentMethod
    public Payment process(PaymentMethod method, double amount) {
        return new Payment(nextId++, amount, method.getName(), method.pay(amount));
    }
}
