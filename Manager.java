package RoniShlomo_And_NikolYosef;

import java.util.Arrays;
import java.util.Comparator;

public class Manager {
    private Seller[] allSellers;
    private Buyer[] allBuyers;
    private int allSellerSize;
    private int allBuyerSize;

    public Manager() {
        allSellers = new Seller[1];
        allBuyers = new Buyer[1];
        allSellerSize = 0;
        allBuyerSize = 0;
    }

    public void addSeller(String SellerName, String password) {
        if (allSellers.length == allSellerSize) {
            Seller[] newSellers = new Seller[allSellerSize * 2];
            System.arraycopy(allSellers, 0, newSellers, 0, allSellerSize);
            allSellers = newSellers;
        }
        allSellers[allSellerSize++] = new Seller(SellerName, password);
    }

    // Check if seller exists
    public boolean isSellerExists(String sellerName) {
        for (int i = 0; i < allSellerSize; i++) {
            if (allSellers[i].getName().equals(sellerName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyBuyer() {
        return allBuyerSize == 0;
    }

    public boolean isEmptySeller() {
        return allSellerSize == 0;
    }

    public void addBuyer(String BuyerName, String password, Address address) {
        if (allBuyers.length == allBuyerSize) {
            Buyer[] newBuyers = new Buyer[allBuyerSize * 2];
            System.arraycopy(allBuyers, 0, newBuyers, 0, allBuyerSize);
            allBuyers = newBuyers;
        }
        allBuyers[allBuyerSize++] = new Buyer(BuyerName, password, address);
    }

    // Check if buyer exists
    public boolean isBuyerExists(String buyerName) {
        for (int i = 0; i < allBuyerSize; i++) {
            if (allBuyers[i].getName().equals(buyerName)) {
                return true;
            }
        }
        return false;
    }

    // Return the list of sellers
    public Seller[] getSeller() {
        Seller[] sellers = new Seller[allSellerSize];
        System.arraycopy(allSellers, 0, sellers, 0, allSellerSize);
        return sellers;
    }

    // return the list of buyers
    public Buyer[] getBuyer() {
        Buyer[] buyers = new Buyer[allBuyerSize];
        System.arraycopy(allBuyers, 0, buyers, 0, allBuyerSize);
        return buyers;
    }

    public void addProduct(String sellerName, Product product) {
        Seller seller = getSellerByName(sellerName);
        seller.addProduct(product);
    }

    public void displayProductsByCategory(Product.eCategory category) {
        boolean foundProducts = false;
        for (int i = 0; i < allSellerSize; i++) {
            Product[] sellerProducts = allSellers[i].getAllProducts();

            for (Product product : sellerProducts) {
                if (product.getCategory() == category) {
                    if (!foundProducts) {
                        System.out.println("Products in category: " + category);
                        foundProducts = true;
                    }
                    if (product instanceof SpecialPackedProduct packedProduct) {
                        System.out.println(product + " price includes packaging fee of: " + packedProduct.getPackingPrice() +" NIS");
                    } else {
                        System.out.println(product);
                    }
                }
            }
        }
        if (!foundProducts) {
            System.out.println("No products found in category: " + category);
        }
    }


    // get seller by name
    public Seller getSellerByName(String sellerName) {
        for (int i = 0; i < allSellerSize; i++) {
            if (allSellers[i].getName().equals(sellerName)) {
                return allSellers[i];
            }
        }
        return null;
    }


    // function to get sorted sellers by product count
    public Seller[] getSortedSellersByProductCount() {
        Seller[] sortedSellers = new Seller[allSellerSize];
        int validSellerCount = 0;

        for (int i = 0; i < allSellerSize; i++) {
            if (allSellers[i] != null) {
                sortedSellers[validSellerCount++] = allSellers[i];
            }
        }

        sortedSellers = Arrays.copyOf(sortedSellers, validSellerCount);

        Arrays.sort(sortedSellers, new Comparator<>() {
            @Override
            public int compare(Seller s1, Seller s2) {
                return Integer.compare(s2.getAllProducts().length, s1.getAllProducts().length);
            }
        });

        return sortedSellers;
    }


    // function to get sorted buyers by name
    public Buyer[] getSortedBuyersByName() {
        Buyer[] sortedBuyers = Arrays.copyOf(allBuyers, allBuyerSize);
        Arrays.sort(sortedBuyers, new Comparator<>() {
            @Override
            public int compare(Buyer b1, Buyer b2) {
                return b1.getName().compareTo(b2.getName());
            }
        });
        return sortedBuyers;
    }


}
