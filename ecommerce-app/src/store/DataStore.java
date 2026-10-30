package store;

import model.User;
import java.util.HashMap;
import java.util.Map;

public class DataStore {
    public static final Map<Integer, User> users = new HashMap<>();
    // each teammate adds their own maps below (products, carts, orders...)
}
