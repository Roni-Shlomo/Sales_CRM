package RoniShlomo_And_NikolYosef;

import java.time.LocalDate;
import java.util.Arrays;

public class Cart {
    private double price;
    private Product[] allProducts;
    private LocalDate buyDate;
    private int currentProductIndex;

    public Cart() {
        this.price = 0;
        this.allProducts = new Product[0];
        this.buyDate = null;
        this.currentProductIndex = 0;
    }

    public double getPrice() {
        return price;
    }

    public double setPrice() {
        double totalPrice = 0.0;
        for (int i = 0; i < currentProductIndex; i++) {
            totalPrice += allProducts[i].getPrice();
        }
        this.price = totalPrice;
        return totalPrice;
    }

    public Product[] getAllProducts() {
        return allProducts;
    }

    public void updateProductArr(Product newProduct) {
        if (allProducts.length == 0) {
            allProducts = new Product[1];
        } else {
            if (currentProductIndex == allProducts.length) {
                Product[] temp = new Product[allProducts.length * 2];
                System.arraycopy(allProducts, 0, temp, 0, allProducts.length);
                allProducts = temp;
            }
        }
        try {
            allProducts[currentProductIndex++] = (Product) newProduct.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        setPrice();
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate() {
        this.buyDate = LocalDate.now();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("price: ").append(price)
                .append(", all products: ").append(Arrays.toString(allProducts))
                .append(", purchase date: ").append(buyDate);
        return sb.toString();
    }
}
