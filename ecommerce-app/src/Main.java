import model.*;
import store.DataStore;

public class Main {
    public static void main(String[] args) {
        DataStore.users.put(1, new Admin(1, "Admin", "admin@shop.com"));
        DataStore.users.put(2, new Seller(2, "Ravi", "ravi@shop.com"));
        DataStore.users.put(3, new Customer(3, "Jeni", "jeni@mail.com"));
        DataStore.users.values().forEach(u -> System.out.println(u.getRole() + ": " + u.getName()));
    }
}
