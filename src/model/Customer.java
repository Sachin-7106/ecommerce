package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private final List<String> addresses = new ArrayList<>();
    private int defaultIndex = 0;

    public Customer(int id, String name, String email) { super(id, name, email); }

    public void addAddress(String address, boolean makeDefault) {
        if (addresses.size() >= 5) throw new IllegalStateException("Maximum 5 addresses allowed");
        addresses.add(address);
        if (makeDefault) defaultIndex = addresses.size() - 1;
    }

    public String getDefaultAddress() { return addresses.isEmpty() ? null : addresses.get(defaultIndex); }

    @Override
    public String getRole() { return "Customer"; }
}
