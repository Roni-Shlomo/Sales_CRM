package RoniShlomo_And_NikolYosef;

import java.util.Arrays;

public class Seller extends User {
    private Product[] allProducts;
    private int currentProductIndex;

    public Seller(String name, String password) {
        super(name, password);
        this.allProducts = new Product[0];
        this.currentProductIndex = 0;
    }

    public Product[] getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(Product[] allProducts) {
        this.allProducts = allProducts;
    }

    //check if product exists
    public boolean isProductExists(String productName) {
        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public void addProduct(Product product) {
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
            allProducts[currentProductIndex++] = (Product) product.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
    public boolean isEmptyProduct() {
        return allProducts == null;
    }

    @Override
    public String toString() {
        String sb = "\n Seller's name: " + getName()+
                "\n Products: \n" + Arrays.toString(allProducts) + '\n' +
                "-------------";
        return sb;
    }

}
