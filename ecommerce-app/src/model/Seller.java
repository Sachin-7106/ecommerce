package model;

public class Seller extends User {
    public Seller(int id, String name, String email) { super(id, name, email); }

    @Override
    public String getRole() { return "Seller"; }
}
