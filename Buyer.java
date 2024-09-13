package RoniShlomo_And_NikolYosef;

import java.util.Arrays;

public class Buyer extends User {
    private Address addresses;
    private Cart shoppingCart;
    private Cart[] ordersHistory;
    private int ordersHistorySize;

    public Buyer(String name, String password, Address addresses) {
        super(name, password);
        this.addresses = addresses;
        this.shoppingCart = new Cart();
        this.ordersHistory = new Cart[0];
        this.ordersHistorySize = 0;
    }

    public Address getAddresses() {
        return addresses;
    }

    public void setAddresses(Address addresses) {
        this.addresses = addresses;
    }

    public Cart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(Cart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Cart[] getOrdersHistory() {
        return ordersHistory;
    }

    public void setOrdersHistory(Cart[] ordersHistory) {
        this.ordersHistory = ordersHistory;
    }

    public void addProductToCart(Product product) {
        shoppingCart.updateProductArr(product);
    }

    public void completePurchase() throws EmptyCartException {
        if (shoppingCart.getAllProducts().length == 0) {
            throw new EmptyCartException("Cannot complete purchase: The shopping cart is empty.");
        }
        shoppingCart.setBuyDate();
        if (ordersHistorySize == 0) {
            ordersHistory = new Cart[1];
        } else {
            if (ordersHistorySize == ordersHistory.length) {
                Cart[] tempArray = new Cart[ordersHistory.length * 2];
                System.arraycopy(ordersHistory, 0, tempArray, 0, ordersHistory.length);
                ordersHistory = tempArray;
            }
        }
        ordersHistory[ordersHistorySize++] = shoppingCart;
        shoppingCart = new Cart(); // Reset the shopping cart
    }
    public boolean isCartEmpty() {
        return shoppingCart.getAllProducts().length == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n')
                .append("Buyer's name: ").append(getName()).append("\n")
                .append("Addresses = ").append(addresses).append("\n").append("Current cart: ").append(shoppingCart)
                .append('\n').append("Order history:").append('\n').append(Arrays.toString(ordersHistory)).append('\n')
                .append("-------------");
        return sb.toString();
    }
}
