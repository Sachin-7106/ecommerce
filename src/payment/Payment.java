package payment;

import model.PaymentStatus;
import java.time.LocalDate;

public class Payment {
    private final int id;
    private final double amount;
    private final LocalDate date = LocalDate.now();
    private final String method;
    private PaymentStatus status;

    public Payment(int id, double amount, String method, PaymentStatus status) {
        this.id = id;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}
