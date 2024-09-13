package RoniShlomo_And_NikolYosef;

public class SpecialPackedProduct extends Product {
    private double packingPrice;

    public SpecialPackedProduct(String name, double price, eCategory category, double packingPrice) {
        super(name, price + packingPrice, category);
        this.packingPrice = packingPrice;
    }

    public SpecialPackedProduct(SpecialPackedProduct special_product) {
        super(special_product.getName(), special_product.getPrice(), special_product.getCategory());
        this.packingPrice = special_product.packingPrice;
    }

    public double getPackingPrice() {
        return packingPrice;
    }

    public void setPackingPrice(double packingPrice) {
        this.packingPrice = packingPrice;
    }

    @Override
    public String toString() {
        return super.toString() + ", Price includes packaging fee of: " + packingPrice + " NIS";
    }
}