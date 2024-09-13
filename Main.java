package RoniShlomo_And_NikolYosef;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner s = new Scanner(System.in);

    public static void menu() {
        Manager manager = new Manager();
        int choice;
        System.out.println("Welcome to 'Buy&Sell' virtual shop!");

        do {
            System.out.println("Choose one of the following options:");
            System.out.println("1 - add a seller");
            System.out.println("2 - add a buyer");
            System.out.println("3 - adding a product to the seller");
            System.out.println("4 - adding a product to a buyer");
            System.out.println("5 - order payment");
            System.out.println("6 - displaying buyers details");
            System.out.println("7 - displaying sellers details");
            System.out.println("8 - displaying all products in the same category");
            System.out.println("9 - create a new cart from order history");
            System.out.println("0 - exit");

            System.out.println("Enter your choice --> ");
            choice = getValidIntInput();

            switch (choice) {
                case 0:
                    System.out.println("Exit the program");
                    break;
                case 1:
                    addNewSeller(manager);
                    break;
                case 2:
                    addNewBuyer(manager);
                    break;
                case 3:
                    addProductToSeller(manager);
                    break;
                case 4:
                    addProductToBuyer(manager);
                    break;
                case 5:
                    orderPayment(manager);
                    break;
                case 6:
                    printBuyers(manager);
                    break;
                case 7:
                    printSellers(manager);
                    break;
                case 8:
                    printProductsFromCategory(manager);
                    break;
                case 9:
                    createNewCartFromHistory(manager);
                    break;
                default:
                    System.out.println("Invalid option, please try again");
                    break;
            }
            System.out.println();
        } while (choice != 0);

        System.out.println("bye!");

    }
    // case 1 //
    public static void addNewSeller(Manager manager) {
        try {
            System.out.println("Enter a seller's name (instead of a space write '_') ");
            String seller_name = getValidStringInput();
            while (manager.isSellerExists(seller_name)) {
                System.out.println("Seller with this name already exists. Choose a different name. ");
                seller_name = getValidStringInput();
            }
            System.out.println("Enter your password ");
            String seller_password = getValidStringInput();
            manager.addSeller(seller_name, seller_password);
            System.out.println("The seller has been successfully added.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // case 2 //
    public static void addNewBuyer(Manager manager) {
        try {
            System.out.println("Enter a buyer's name (instead of a space write '_')");
            String buyer_name = getValidStringInput();
            while (manager.isBuyerExists(buyer_name)) {
                System.out.println("Buyer with this name already exists. Choose a different name. ");
                buyer_name = getValidStringInput();
            }
            System.out.println("Enter your password ");
            String buyer_password = getValidStringInput();

            System.out.println("Enter your street address ");
            String buyer_street = getValidStringInput();
            System.out.println("Enter your street number ");
            int buyer_street_number = getValidIntInput();
            System.out.println("Enter your city address ");
            String buyer_city = getValidStringInput();
            System.out.println("Enter your country address ");
            String buyer_country = getValidStringInput();

            manager.addBuyer(buyer_name, buyer_password, new Address(buyer_street, buyer_street_number, buyer_city, buyer_country));
            System.out.println("The buyer has been successfully added");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // case 3 //
    public static void addProductToSeller(Manager manager) {
        try {
            if (manager.isEmptySeller()) {
                throw new EmptySellerException("There are no sellers, you need to add a seller first.");
            }
            System.out.println("These are all the sellers' names: ");
            Seller[] tempSeller = manager.getSeller();
            for (Seller seller : tempSeller) {
                System.out.println(seller.getName());
            }
            System.out.println("Write the seller's name you want to add a product to:");
            String pick_seller_name = getValidStringInput();

            while (!manager.isSellerExists(pick_seller_name)) {
                System.out.println("There is no a seller with this name, please try again. ");
                pick_seller_name = getValidStringInput();
            }
            System.out.println("Please choose a category number (not name)  for the product:");
            for (Product.eCategory category : Product.eCategory.values()) {
                System.out.println(category.ordinal() + 1 + ". " + category);
            }
            int categoryChoice = getValidIntInput();
            // check if the category number valid
            while (!Product.ifValidCategoryChoice(categoryChoice)){
                System.out.println("Invalid choice. Please enter a valid category number.");
                categoryChoice = getValidIntInput();
            }
            Product.eCategory selectedCategory = Product.eCategory.values()[categoryChoice - 1];

            Seller selectedSeller = manager.getSellerByName(pick_seller_name);
            System.out.println("Enter the product name you want to add for the seller:");
            String pick_product_name = getValidStringInput();

            // Check if the product already exists for this seller
            while(selectedSeller.isProductExists(pick_product_name)){
                System.out.println("A product with this name already exists for the seller. Please choose a different name:");
                pick_product_name = getValidStringInput();
            }
            System.out.println("Enter the product's price");
            double pick_product_price = getValidDoubleInput();

            System.out.println("Do you want to add this product with packing? yes / no");
            String ans_pack = getValidStringInput();
            Product product;
            while (!ans_pack.equals("yes") && !ans_pack.equals("no")){
                System.out.println("You need to write only yes / no : ");
                ans_pack = s.next();
            }
            if (ans_pack.equals("yes")){
                System.out.println("Enter the packing price");
                double pack_price = getValidDoubleInput();
                product = new SpecialPackedProduct(pick_product_name, pick_product_price, selectedCategory, pack_price);
                System.out.println("The product has been successfully packaged");
            } else {
                product = new Product(pick_product_name, pick_product_price, selectedCategory);
            }
            manager.addProduct(pick_seller_name, product);
            System.out.println("The product has been successfully added to the requested seller");
        } catch (EmptySellerException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // case 4 //
    public static void addProductToBuyer(Manager manager) {
        try {
            if (manager.isEmptyBuyer()) {
                throw new EmptyBuyerException("There are no buyers, you need to add a buyer first.");
            }
            if (manager.isEmptySeller()) {
                throw new EmptySellerException("There are no sellers, you need to add a seller first.");
            }

            Buyer current_buyer = null;
            Seller current_seller = null;
            Product current_product = null;

            // Requests for buyer name until a valid one is provided
            Buyer[] tempBuyer2 = manager.getBuyer();
            System.out.println("Type a buyer name from the list:");
            for (Buyer buyer : tempBuyer2) {
                System.out.println(buyer.getName());
            }
            while (current_buyer == null) {
                String pick_buyer = getValidStringInput();
                for (Buyer buyer : tempBuyer2) {
                    if (buyer.getName().equals(pick_buyer)) {
                        current_buyer = buyer;
                        break;
                    }
                }
                if (current_buyer == null) {
                    System.out.println("There is no buyer with this name, please try again.");
                }
            }
            // Requests for seller name until a valid one is provided
            Seller[] tempSeller2 = manager.getSeller();
            System.out.println("Type a seller name from the list:");
            for (Seller seller : tempSeller2) {
                System.out.println(seller.getName());
            }
            while (current_seller == null) {
                String pick_seller = getValidStringInput();
                for (Seller seller : tempSeller2) {
                    if (seller.getName().equals(pick_seller)) {
                        current_seller = seller;
                        break;
                    }
                }
                if (current_seller == null) {
                    System.out.println("There is no seller with this name, please try again.");
                }
            }
            if (current_seller.getAllProducts().length == 0) {
                System.out.println("There are no products for the requested seller.");
                return;
            }
            // Display the seller's products
            System.out.println("These are the seller's products:");
            for (Product product : current_seller.getAllProducts()) {
                if (product != null) {
                    System.out.println(product);
                }
            }

            // Requests for product name until a valid one is provided
            System.out.println("Enter the product name you want to buy:");
            while (current_product == null) {
                String pick_product = getValidStringInput();
                for (Product product : current_seller.getAllProducts()) {
                    if (product != null && product.getName().equals(pick_product)) {
                        current_product = product;
                        break;
                    }
                }
                if (current_product == null) {
                    System.out.println("Product with this name does not exist. Please try again:");
                }
            }
            // Add the product to the buyer's cart
            current_buyer.addProductToCart(current_product);
            System.out.println("Product added to cart successfully.");
        } catch (EmptyBuyerException | EmptySellerException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // case 5 //
    public static void orderPayment(Manager manager) {
        try {
            if (manager.isEmptyBuyer()) {
                throw new EmptyBuyerException("There are no buyers, you need to add a buyer first.");
            }
            System.out.println("Select a buyer to complete the purchase:");
            Buyer[] tempBuyer3 = manager.getBuyer();
            for (Buyer buyer : tempBuyer3) {
                System.out.println(buyer.getName());
            }
            String pick_buyer_to_complete = getValidStringInput();
            while (!manager.isBuyerExists(pick_buyer_to_complete)) {
                System.out.println("There is no a buyer with this name, please try again. ");
                pick_buyer_to_complete = getValidStringInput();
            }
            for (Buyer buyer : tempBuyer3) {
                if (buyer.getName().equals(pick_buyer_to_complete)) {
                    try {
                        System.out.println("Cart total price: " + buyer.getShoppingCart().getPrice());
                        buyer.completePurchase();
                        System.out.println("Purchase completed.");

                    } catch (EmptyCartException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } catch (EmptyBuyerException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // case 6 //
    public static void printBuyers(Manager manager){
        System.out.println("|Details of all buyers|");
        Buyer[] buyers = manager.getSortedBuyersByName();
        if (buyers.length > 0){
            for (Buyer buyer : buyers) {
                System.out.println(buyer.toString());
            }
        }
        else{
            System.out.println("There are no buyers. Please add a buyer first");
        }

    }

    // case 7 //
    public static void printSellers(Manager manager){
        System.out.println("|Details of all sellers|");
        Seller[] sellers = manager.getSortedSellersByProductCount();
        if (sellers.length > 0){
            for (Seller seller : sellers) {
                System.out.println(seller.toString());
            }
        }
        else{
            System.out.println("There are no sellers. Please add a seller first");
        }

    }
    // case 8 //
    public static void printProductsFromCategory(Manager manager){
        System.out.println("Please choose a category number to display all the products in it:");
        for (Product.eCategory category : Product.eCategory.values()) {
            System.out.println(category.ordinal() + 1 + ". " + category);
        }
        int categoryChoiceToPrint = getValidIntInput();
        // check if the category number valid
        while (!Product.ifValidCategoryChoice(categoryChoiceToPrint)){
            System.out.println("Invalid choice. Please enter a valid category number.");
            categoryChoiceToPrint = getValidIntInput();
        }
        Product.eCategory selectedCategoryToPrint = Product.eCategory.values()[categoryChoiceToPrint - 1];
        manager.displayProductsByCategory(selectedCategoryToPrint);
    }

    // case 9 //
    public static void createNewCartFromHistory(Manager manager) {
        try {
            if (manager.isEmptyBuyer()) {
                throw new EmptyBuyerException("There are no buyers, you need to add a buyer first.");
            }
            System.out.println("Select a buyer to create a new cart from order history:");
            Buyer[] tempBuyer4 = manager.getBuyer();
            for (Buyer buyer : tempBuyer4) {
                System.out.println(buyer.getName());
            }
            String pick_buyer_to_new_cart = getValidStringInput();
            while (!manager.isBuyerExists(pick_buyer_to_new_cart)) {
                System.out.println("There is no buyer with this name, please try again.");
                pick_buyer_to_new_cart = getValidStringInput();
            }
            for (Buyer buyer : tempBuyer4) {
                if (buyer.getName().equals(pick_buyer_to_new_cart)) {
                    if (buyer.getShoppingCart().getAllProducts().length > 0) {
                        System.out.println("Your shopping cart is not empty. Do you want to replace it? yes / no");
                        String replaceCart = getValidStringInput();
                        while (!replaceCart.equals("yes") && !replaceCart.equals("no")) {
                            System.out.println("You need to write only yes / no:");
                            replaceCart = getValidStringInput();
                        }
                        if (replaceCart.equals("no")) {
                            System.out.println("The current cart was not replaced.");
                        } else {
                            if (buyer.getOrdersHistory().length == 0) {
                                System.out.println("No previous orders found.");
                            } else {
                                System.out.println("Select an order number from your order history:");
                                for (int i = 0; i < buyer.getOrdersHistory().length; i++) {
                                    if (buyer.getOrdersHistory()[i] != null) { // check for null
                                        System.out.println((i + 1) + ". Order from " + buyer.getOrdersHistory()[i].getBuyDate());
                                        System.out.println("Products: " + Arrays.toString(buyer.getOrdersHistory()[i].getAllProducts()));
                                    }
                                }
                                int orderChoice = getValidIntInput();
                                while (orderChoice < 1 || orderChoice > buyer.getOrdersHistory().length || buyer.getOrdersHistory()[orderChoice - 1] == null) { // check for valid choice and non-null order
                                    System.out.println("Invalid choice. Please select a valid order number.");
                                    orderChoice = getValidIntInput();
                                }
                                Cart selectedOrder = buyer.getOrdersHistory()[orderChoice - 1];
                                buyer.setShoppingCart(new Cart()); // Reset the current cart
                                for (Product product : selectedOrder.getAllProducts()) {
                                    buyer.addProductToCart((Product) product.clone());
                                }
                                System.out.println("The cart has been successfully replaced with the selected order.");
                            }
                        }
                    } else {
                        if (buyer.getOrdersHistory().length == 0) {
                            System.out.println("No previous orders found.");
                        } else {
                            System.out.println("Select an order number from your order history:");
                            for (int i = 0; i < buyer.getOrdersHistory().length; i++) {
                                if (buyer.getOrdersHistory()[i] != null) { // check for null
                                    System.out.println((i + 1) + ". Order from " + buyer.getOrdersHistory()[i].getBuyDate());
                                    System.out.println("Products: " + Arrays.toString(buyer.getOrdersHistory()[i].getAllProducts()));
                                }
                            }
                            int orderChoice = getValidIntInput();
                            while (orderChoice < 1 || orderChoice > buyer.getOrdersHistory().length || buyer.getOrdersHistory()[orderChoice - 1] == null) { // check for valid choice and non-null order
                                System.out.println("Invalid choice. Please select a valid order number.");
                                orderChoice = getValidIntInput();
                            }
                            Cart selectedOrder = buyer.getOrdersHistory()[orderChoice - 1];
                            buyer.setShoppingCart(new Cart()); // Reset the current cart
                            for (Product product : selectedOrder.getAllProducts()) {
                                buyer.addProductToCart((Product) product.clone());
                            }
                            System.out.println("The cart has been successfully replaced with the selected order.");
                        }
                    }
                }
            }
        } catch (EmptyBuyerException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static int getValidIntInput() {
        while (true) {
            try {
                return s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer:");
                s.next(); // clear the invalid input
            }
        }
    }
    public static double getValidDoubleInput() {
        while (true) {
            try {
                return s.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid decimal number:");
                s.next(); // clear the invalid input
            }
        }
    }

    public static String getValidStringInput() {
        while (true) {
            String input = s.next();
            if (!input.trim().isEmpty()) {
                return input;
            } else {
                System.out.println("Invalid input. Please enter a non-empty string:");
            }
        }
    }
    public static void main(String[] args){
        menu();
        s.close();

    }
}