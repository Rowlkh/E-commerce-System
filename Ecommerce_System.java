/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package fawry_challenge;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Roaa
 */

public class Ecommerce_System {

    public static ArrayList<Product> products = new ArrayList<>();
    public static HashMap<Product, Integer> cart = new HashMap<>();
    public static double customerBalance = 1000.0;

    public static void main(String[] args) {
        initializeProducts();

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayProducts();

            System.out.print("\nChoose an option (1. Display Cart | 2. Checkout | 3. Add to Cart): ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    displayCart();
                    break;
                case 2:
                    checkout();
                    break;
                case 3:
                    addToCart(sc);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            System.out.print("\nDo you want to continue? (1 = yes / 0 = no): ");
            int cont = sc.nextInt();
            if (cont == 0) running = false;
        }

        System.out.println("Thank you for using the system.");
        sc.close();
    }

    public static void initializeProducts() {
        products.add(new Product("Cheese", 90, 10, LocalDateTime.now().plusMonths(6), true, 0.5));
        products.add(new Product("Laptop", 15000, 5, null, true, 2.3));
        products.add(new Product("Perfume", 500, 20, null, true, 0.3));
        products.add(new Product("Yogurt", 30.5, 50, LocalDateTime.now().plusWeeks(2), true, 0.2));
        products.add(new Product("Biscuits", 60, 100, LocalDateTime.now().plusMonths(4), true, 0.7));
        products.add(new Product("TV", 12000, 7, null, true, 8.5));
        products.add(new Product("Selfie Stick", 50, 200, null, false, 0.0));
        products.add(new Product("Headphones", 600, 15, null, true, 0.4));
    }

    public static void displayProducts() {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("| # | Name           | Price (EGP)| Quantity | Shippable       | Expiration   |\n");
        System.out.println("---------------------------------------------------------------------------------------");

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            String shippable = p.getShippable() ? "Yes (" + p.getWeight() + " kg)" : "No";
            String expiry = (p.getExpiration_date() != null) ? p.getExpiration_date().toLocalDate().toString() : "N/A";
            System.out.printf("| %-1d | %-14s | %-10.2f | %-8d | %-15s | %-12s |\n",
                    (i + 1), p.getName(), p.getPrice(), p.getQuantity(), shippable, expiry);
        }

        System.out.println("---------------------------------------------------------------------------------------");
    }

    public static void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("| # | Name           | Price (EGP)| Quantity | Total (EGP) |\n");
        System.out.println("---------------------------------------------------------------");

        int index = 1;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double total = p.getPrice() * qty;
            System.out.printf("| %-1d | %-14s | %-10.2f | %-8d | %-10.2f |\n",
                    index++, p.getName(), p.getPrice(), qty, total);
        }

        System.out.println("---------------------------------------------------------------");
    }

    public static void addToCart(Scanner sc) {
        System.out.print("Enter the product number to add to cart: ");
        int productNumber = sc.nextInt();

        if (productNumber < 1 || productNumber > products.size()) {
            System.out.println("Invalid product number.");
            return;
        }

        Product selectedProduct = products.get(productNumber - 1);

        if (selectedProduct.getExpiration_date() != null &&
                selectedProduct.getExpiration_date().isBefore(LocalDateTime.now())) {
            System.out.println("Product expired.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        if (quantity < 1 || quantity > selectedProduct.getQuantity()) {
            System.out.println("Invalid quantity.");
            return;
        }

        selectedProduct.setQuantity(selectedProduct.getQuantity() - quantity);
        cart.put(selectedProduct, cart.getOrDefault(selectedProduct, 0) + quantity);

        System.out.println(quantity + " x " + selectedProduct.getName() + " added to cart.");
    }

    public static void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        double subtotal = 0;
        double shippingWeight = 0;

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();

            if (p.getExpiration_date() != null && p.getExpiration_date().isBefore(LocalDateTime.now())) {
                System.out.println("Product " + p.getName() + " has expired. Cannot proceed.");
                return;
            }

            subtotal += p.getPrice() * qty;

            if (p.getShippable()) {
                shippingWeight += p.getWeight() * qty;
            }
        }

        double shippingFee = shippingWeight > 0 ? 20 + (5 * shippingWeight) : 0;
        double total = subtotal + shippingFee;

        if (customerBalance < total) {
            System.out.println("Insufficient balance.");
            return;
        }

        if (shippingWeight > 0) {
            System.out.println("\n** Shipment Notice **");
            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                Product p = entry.getKey();
                if (p.getShippable()) {
                    System.out.println(entry.getValue() + "x " + p.getName() + " " + (p.getWeight() * entry.getValue()) + " kg");
                }
            }
            System.out.printf("Total package weight: %.2f kg\n", shippingWeight);
        }

        System.out.println("\n** Checkout Receipt **");
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.println(qty + "x " + p.getName() + " " + (p.getPrice() * qty) + " EGP");
        }

        System.out.println("----------------------");
        System.out.printf("Subtotal: %.2f EGP\n", subtotal);
        System.out.printf("Shipping: %.2f EGP\n", shippingFee);
        System.out.printf("Total: %.2f EGP\n", total);

        customerBalance -= total;
        System.out.printf("Customer Balance: %.2f EGP\n", customerBalance);

        cart.clear();
    }
}
