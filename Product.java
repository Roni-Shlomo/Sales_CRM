package RoniShlomo_And_NikolYosef;

public class Product implements Cloneable {
    private static int serialCounter;
    protected final int serialNumber;
    protected String name;
    protected double price;
    protected final eCategory category;

    public enum eCategory {
        KIDS,
        ELECTRONICS,
        OFFICE,
        CLOTHING
    }

    public Product(String name, double price, eCategory category) {
        this.serialNumber = ++serialCounter;
        this.name = name;
        this.price = price;
        this.category = category;
    }
    public Product(Product other) {
        this.serialNumber = other.serialNumber;
        this.name = other.name;
        this.price = other.price;
        this.category = other.category;
    }

    public eCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public static boolean ifValidCategoryChoice(int categoryChoice) {
        return categoryChoice >= 1 && categoryChoice <= eCategory.values().length;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": ").append(price).append(" NIS, serial number: ").append(serialNumber).append(", category: ").append(category).append('\n');
        return sb.toString();
    }
}